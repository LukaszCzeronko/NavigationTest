# Navigation - brief summary.
The main task of our project is to interpolate points on map with given accuracy based on car speed and sampling time.

# Program arguments
- mandatory:
    * ```-rn``` number of random routes.
    * ```-ml``` maximum length of route.
 
 - optional:
    * ```-in, -input ``` file with location points(lat/long). Default: locations.json
    * ```-u, -units ``` unit system: METRIC or IMPERIAL. Default: METRIC
    * ```-s, -speed``` speed of a car. Default: 90.0
    * ```-i,- interval ``` sampling time in seconds. Default: 180
    * ```-0, -output ``` file to store original route points. Default: route.json
    * ```-c, -config ``` input for route distribution json file. Default: configuration.json
    * ```-r, -results ``` csv file with query string. Default: csvRouteRequest.csv
    * ```-d, -debug ``` debug option, show full response and calculated points. Default: false
 
 # Run
 Set Maven program arguments:
 
 default-  ```-rn 1 -ml 100``` - one random route, max route distance set to 100. All optional arguments set to default.
 
 example:
 * ```-rn 6 -ml 100 -i 30 -d=true``` generate 6 random routes, max route distance set to 100, sampling time set to 30 seconds, debug mode on.
 * ```-rn 6 -ml 200 -i 30 -s 60 -d=true -u IMPERIAL``` generate 6 random routes, max route distance set to 200, sampling time set to 30 seconds, speed set to 60, debug mode on, unit type set to imperial.
 
 
 
 