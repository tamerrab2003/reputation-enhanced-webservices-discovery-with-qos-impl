prerequesets:
- maven
- eclipse(recommended)
- maven plugin to eclipse(recommended)
- uddi reposetory implementation (juddi-portal-bundle)

Installation istructions:

- get source code through git or download source code zip file;
- from files folder download juddi-portal-bundle-3.0.3.zip file
- extract juddi-portal-bundle-3.0.3.zip file
- from command line run juddi-portal-bundle-3.0.3\bin\starup.bat(now juddi is running)
- import projects into eclipse (from file > import > existing project from file system; and browse to your source code folder)
- update maven dependences (right click on each project select maven > update project configurations)
- open App.java from service-provider project, right click and select run as > java application
- open ServiceFinderImpl.java from DiscoveryAgent project select run as > java application
- open ConsumerService.java from Consumer project select run as > java application

rusults should applear on console

