#!/bin/sh

echo "minifying javascript"

cp jquery-2.1.3.js jquery.min.js

java -jar compiler.jar --js jquery.maskedinput.js --js_output_file maskedinput.min.js
java -jar compiler.jar --js jquery.plugins.js --js_output_file jquery.plugins.min.js