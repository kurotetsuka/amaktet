#globals
default: build
freshen: clean build
clean:
	rm -rf bin/*
	rm -rf pkg/*
	rm -rf jar/*

#variables
version = 0.0.1
cp = bin:lib/*:lib/lwjgl/jar/*
dest = -d bin
natives = -Djava.library.path=lib/lwjgl/native/linux
jar_file = jar/amaktet-$(version).jar
package_file = pkg/amaktet-$(version).tar.gz
options =
warnings =
#warnings = -Xlint:deprecation

#includes
include lists.mk
include dependencies.mk

#compilation definitions
$(class_files): bin/%.class : src/%.java
	javac -cp $(cp) $(dest) $(warnings) $<

#commands
build: $(class_files)

run: build
	java -cp $(cp) $(natives) kuro.amaktet.Driver test

$(jar_file): $(class_files) manifest.mf
	rm -rf jar/*
	jar cmf manifest.mf $@ -C bin kuro
jar: $(jar_file)
jar-test: jar
	java -cp lib/*:lib/lwjgl/jar/* -jar $(jar_file)

$(package_file): \
		$(class_files) $(jar_file) $(docs_path) \
		readme.md license.md
	tar -cf $(package_file) $(docs_path) $(jar_file) readme.md license.md
package: $(package_file)
package-test: package
	file-roller $(package_file) &

#other commands
git-prepare:
	git add -u
	git add -A

#test commands
test: run

test-cursor: bin/test/CursorTest.class
	java -cp $(cp) $(natives) test.CursorTest

test-inputstreamhandler: bin/kuro/amaktet/test/TestInputStreamHandler.class
	java -cp $(cp) kuro.amaktet.test.TestInputStreamHandler

test-console: bin/kuro/amaktet/test/TestConsole.class
	java -cp $(cp) kuro.amaktet.test.TestConsole
