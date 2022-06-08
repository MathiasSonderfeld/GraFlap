# GraFlap: ProformA 2.1 speaking Grading-Engine for JFLAP files and other theoretical computer science tasks

GraFlap has 2 operation modes, legacy and proforma. I recommend to use the proforma mode as the legacy mode does not have the full feature set.

## Branches
There are two branches for GraFLAP, master and MavenLibs. The difference is only in the library version used. In the master branch the old library versions get used, that have been used in production prior to my work. In MavenLibs newer version from the Maven Central Repository are used.
The tests can not secure that all functionality is worked as expected with the newer versions and the changelogs are not completely available. So I offer a safer version.
I recommend to MavenLibs branch for future development.

## Build
To build project into an executable `jar` easily use Maven:
```
mvn verify
```
This will build all classes in `target/` directory, then build a graflap-1.0.jar thats not executable and finally a graflap-1.0-jar-with-dependencies.jar, that is executable.

If that build fails because it cant find the dependencies in the public repo, just run maven again.
This should be fixed now, so please report to me if thats still neccessary.
If youre on MacOS, you might have to skip the tests. I dont know why they fail as I dont have access to a Mac currently.
Linux and Windows get verified to work.

### Dependencies
GraFlap uses a local repository for the most libraries as these are not available on the main repo in that version anymore.
These are provided in the repository in the lib folder so no further action is necessary.

Apart form these, Junit-Juniper and Mockito are required to build. Maven will download them from the public repo if not already available locally.

For running the program you also need GraphViz (http://graphviz.org/).
Make sure the executables are globally available by adding them to the PATH variable.
The Windows Installer asks, if you want to, select yes.
For Linux the installer should do that without prompt.
You can verify this if you can execute "fdp" and get an empty prompt as the tool expects further parameters.

## Usage
### Legacy
to call GraFlap in legacy mode call the program with 2 arguments.
First the task declaration according to the standard explained further below followed by the content of the JFLAP file or answer you want graded.

### ProformA
There are 2 options to run GraFlap in ProformA mode. Either you provide a file or add the xml document as command line argument.
To grade a file input, use the parameter -f followed by the path to the file so e.g.: java -jar graflap.jar -f submission.xml
to grade a command line argument, use the parameter -s followed by the xml document so e.g.: java -jar graflap. jar -s <xml ....

## ProformA Document restrictions
GraFlap demands a single submission with task and all files embedded according to the ProformA 2.1 standard.
Within that task one embedded file has to carry the ID "graflap-arguments".
In that embedded file the task declaration has to be provided.

In the submission more than one embedded file can be processed depending on task declaration.
At least one needs to be provided identified by its ID. The ID has to match one of the following values depending on the task declaration.

### task declaration
You need to provide these infos:
- a title, can be any string
- a language short tag according to the ISO 639-1 Code. All languages are accepted but currently only english (default) and german output are supported.
- a reference to compare results to. Input type depends on task mode.
- a value for the task mode. A detailed explanation what these are will be added shortly.
- the correct task type. A detailed explanation will follow.
- the number of testwords provided or to be generated
- the testwords in testwords format, thats explained below, or '-' for no words provided.

All these have to be put behind each other in this order with a '#' as delimiter.
The result looks like this: title#langTag#reference#mode#type#number#testwords

### test words format
The test words need to be formatted depending on your task mode.
For cyk and der modes you just pass a single word.
For MMW mode you just enter a single list of words split by "%".
For MP mode a list of word pairs is expected. The pairs are seperated by "%" while the single words within a pair a divided by ";".
For any other mode you need two lists of words, a correct and a failing list. The lists get seperated by a "!", while the single words within the list get seperated by "%".
