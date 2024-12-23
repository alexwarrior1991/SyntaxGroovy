package com.alejandro.basics

//Quoted identifiers
def map = [:];
map."an identifier with a space and double quotes" = "ALLOWED";
map.'with-dash-signs-and-single-quotes' = "ALLOWED";

assert map."an identifier with a space and double quotes" == "ALLOWED"
assert map.'with-dash-signs-and-single-quotes' == "ALLOWED"


/*Groovy provides different string literals. All kind of strings are actually allowed after the dot:*/
map.'single quote' = "test"
map."double quote" = "test"
map.'''triple single quote''' = "test"
map."""triple double quote""" = "test"
map./slashy string/ = "test"
map.$/dollar slashy string/$ = "test"

/*There’s a difference between plain character strings and Groovy’s GStrings (interpolated strings),
 as in that the latter case, the interpolated values are inserted in the final string for evaluating the whole identifier:*/

def firstname = "Homer";
map."Simpson-${firstname}" = "Homer Simpson";
assert map.'Simpson-Homer' == "Homer Simpson"

println(map);


