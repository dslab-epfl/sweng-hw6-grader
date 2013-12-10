# sweng-hw6-grader

A small web application that computes scores for homework six of EPFL's
software engineering course. It aggregates data from Jenkins and SonarQube.

## Usage

To run this on your computer, use

    lein ring server

You also need to set a number of environment variables, notably
GITHUB\_OAUTH\_TOKEN and SONARQUBE\_ADMIN\_PASSWORD.

## License

Copyright © 2013 Jonas Wagner

Distributed under the GNU Affero General Public License either version 3 or (at
your option) any later version.
