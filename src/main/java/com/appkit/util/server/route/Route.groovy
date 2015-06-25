package com.appkit.util.server.route

import groovy.servlet.ServletCategory
import groovy.transform.ToString

import javax.servlet.http.HttpServletRequest
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Representation of a route URL mapping.
 *
 * @author Guillaume Laforge
 */
@ToString
class Route {
    /** The route pattern */
    String route

    /** The destination pattern when the route is matched */
    String destination

    /** The HTTP method used to reach that route */
    HttpMethod method

    /** Whether we're doing a redirect or a forward to the new location */
    RedirectionType redirectionType

    /** The time in seconds the resource to stay in memcache */
    int cacheExpiration

    /** Closure defining a namespace for the scope of the request */
    Closure namespace

    /* The list of variables in the route */
    private List variables

    /* The real regex pattern used for matching URIs */
    private Pattern regex

    /* Closure validating the variables match the required regex patterns */
    private Closure validator

    /** Should a uri matching this route just be ignored? */
    boolean ignore

    /**
     * Constructor taking a route, a destination, an HTTP method (optional), a redirection type (optional),
     * and a closure for validating the variables against regular expression patterns.
     */
    Route(String route, String destination, HttpMethod method = HttpMethod.ALL,
          RedirectionType redirectionType = RedirectionType.FORWARD, Closure validator = null,
          Closure namespace = null, int cacheExpiration = 0, boolean ignore = false) {
        this.route = route
        this.method = method
        this.redirectionType = redirectionType
        this.namespace = namespace
        this.cacheExpiration = cacheExpiration
        this.validator = validator
        this.ignore = ignore

        // extract the path variables from the route
        this.variables = extractParameters(route)

        // create a regular expression out of the route string
        this.regex = Pattern.compile(transformRouteIntoRegex(route))

        this.destination = destination
    }

    /**
     * Extract a list of parameters in the route URI.
     */
    static List<String> extractParameters(String route) {
        route.findAll(/@\w*/)
    }

    /**
     * Transform a route pattern into a proper regex pattern.
     */
    static String transformRouteIntoRegex(String route) {
        route.replaceAll('\\.', '\\\\.')
                .replaceAll('\\*\\*', '(?:.+\\/?){0,}')
                .replaceAll('\\*', '.+')
                .replaceAll('@\\w+', '(.+)')
    }

    /**
     * Checks whether a URI matches a route.
     *
     * @return a map with a 'matches' boolean key telling whether the route is matched
     * and a variables key containing a map of the variable key and matched value.
     */
    def forUri(HttpServletRequest request) {
        def uri = request.requestURI

        // disregarding URL parts with appended ;jsessionid=xxx
        int cutoff = uri.indexOf(';')
        if (cutoff > -1) {
            uri = uri.substring(0, cutoff)
        } else {
            // disregarding URL parts with parameters ?x=y
            cutoff = uri.indexOf('?')
            if (cutoff > -1) {
                uri = uri.substring(0, cutoff)
            }
        }

        Matcher matcher = regex.matcher(uri)

        String finalDestination = destination instanceof String || ignore == true ?
                destination : destination.finalDestination

        if (matcher.matches()) {
            def variableMap = variables ?
                    // a map like ['year': '2009', 'month': '11']
                    variables.inject([:]) { map, variable ->
                        map[variable.substring(1)] = matcher[0][map.size() + 1]
                        return map
                    } : [:] // an empty variables map if no variables were present

            // if a closure validator was defined, check all the variables match the regex pattern
            if (validator) {
                def clonedValidator = this.validator.clone()

                // adds the request to the variables available for validation
                def variablesAndRequest = variableMap.clone()
                variablesAndRequest.request = request

                clonedValidator.delegate = variablesAndRequest
                clonedValidator.resolveStrategy = Closure.DELEGATE_ONLY

                boolean validated = false
                use(ServletCategory, CaelyfCategory) {
                    validated = clonedValidator()
                }
                if (!validated) {
                    return [matches: false]
                }
            }

            // replace all the variables
            def effectiveDestination = variableMap.inject(finalDestination) { String dest, var ->
                dest.replaceAll('@' + var.key, var.value)
            }

            def result = [matches: true, variables: variableMap, destination: effectiveDestination]

            // if a closure namespace was defined, clone it, and inject the variables if any
            if (namespace) {
                def ns = namespace.clone()
                ns.resolveStrategy = Closure.DELEGATE_ONLY
                ns.delegate = variableMap

                // add the namespace to the found matching route
                result.namespace = ns()
            }

            request.setAttribute("originalURI", uri)

            return result
        } else {
            [matches: false]
        }
    }
}