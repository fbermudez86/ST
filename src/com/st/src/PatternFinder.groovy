package com.st.src

class PatternFinder
{

	void findInPath(baseDirectory, pattern, newString)
	{
		def directory = new File(baseDirectory);
		def filePattern = ~/${pattern}/
		//def outputFile = args[3];


		//Si no es un directorio
		if(!directory.isDirectory())
		{
			println "The provided directory name ${baseDirectory} is NOT a directory."
			System.exit(-2)
		}
		directory.eachFileRecurse(findFilenameClosure);
	}
	def findFilenameClosure =
	{
		println "\t${it.name} (size ${it.size()})"
	}
}