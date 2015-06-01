function focusable(element, hasTabindex) {
    var map, mapName, img,
        nodeName = element.nodeName.toLowerCase();
    if ("area" === nodeName) {
        map = element.parentNode;
        mapName = map.name;
        if (!element.href || !mapName || map.nodeName.toLowerCase() !== "map") {
            return false;
        }
        img = $("img[usemap='#" + mapName + "']")[0];
        return !!img && visible(img);
    }
    return ( /^(input|select|textarea|button|object)$/.test(nodeName) ?
            !element.disabled :
            "a" === nodeName ?
            element.href || hasTabindex :
                hasTabindex ) &&
            // the element and all of its ancestors must be visible
        visible(element);
}

function visible(element) {
    return $.expr.filters.visible(element) && !$(element).parents().addBack().filter(function () {
            return $.css(this, "visibility") === "hidden";
        }).length;
}

$.extend($.expr[":"], {
    focusable: function (element) {
        return focusable(element, $.attr(element, "tabindex") != null);
    },

    tabbable: function (element) {
        var tabIndex = $.attr(element, "tabindex"),
            hasTabindex = tabIndex != null;
        return ( !hasTabindex || tabIndex >= 0 ) && focusable(element, hasTabindex);
    }
});