package com.st.src
import java.io.IOException;

class PatternFinder
{
	def directory;
	def filePattern;
	def baseDirectory;
	def outputFileName;
	def pattern;
	def newString;
	def resultTextBuilder = ''<<'';

	PatternFinder(String[] params)
	{
		resultTextBuilder <<= 'App started!!\n';
		this.baseDirectory = params[0];
		this.pattern = params[1];
		this.newString = params[2];
		this.outputFileName = params[3];
		directory = new File(baseDirectory);
		filePattern = ~/${pattern}/;
	}

	void findInPath()
	{
		if(!directory.isDirectory())
		{
			println "The provided directory name ${baseDirectory} is NOT a directory."
			System.exit(-2)
		}
		//Call the closure for each file
		directory.eachFileRecurse(findPatternClosure);

		resultTextBuilder <<= String.format("Creating output file at: %s \n", this.outputFileName);

		//Write the log file
		def outputFile = new File(this.outputFileName);
		outputFile.write(resultTextBuilder.toString());
		println resultTextBuilder;
	}

	def findPatternClosure =
	{
		try
		{
			def tempFile = new File(it.toString());
			resultTextBuilder <<= String.format("Processing File: %s \n", it.toString());
			resultTextBuilder <<= String.format("File Properties: Writable: %s, Readable: %s, isDirectory: %s  \n", tempFile.canWrite(),tempFile.canRead(), tempFile.isDirectory());
			//Is it a directory?
			//It has to be writable and readable
			if(!tempFile.isDirectory() && tempFile.canWrite() && tempFile.canRead())
			{
				if(tempFile.getText().find(filePattern) != null)
				{
					resultTextBuilder <<= String.format("File: %s matched the pattern %s  \n", it.name, pattern);
					//Pattern Found - So, log the relevant data.

					//Original content of the file
					def fileContent = tempFile.getText();

					//Update the file with the result of the ReplaceAll
					tempFile.write(replaceText(fileContent));


					//Define the backup file
					def backupFilename = tempFile.toString().replaceFirst(~/.[^.]+$/, '');
					def backupFile = new File(backupFilename + "-copy." + getExtensionFromFilename(tempFile.name));

					resultTextBuilder <<= String.format("Creating backup file: %s  \n", backupFile.name);
					//If it doesn´t exist
					if(!backupFile.exists())
					{
						backupFile.createNewFile();
						resultTextBuilder <<= String.format("Backup file created: %s  \n", backupFile.name);
					}
					//write the content of the original file
					backupFile.write(fileContent);
				}
			}
			else
			{
				resultTextBuilder <<= String.format("Exploring inside of folder: %s  \n", tempFile.name);
			}	
		}
		catch(IOException ex)
		{
			resultTextBuilder <<= String.format("An error has been occurred on file: %s\n Stack Trace: \n %s", it.toString(), ex.toString());
		}
		catch(Exception ex)
		{
			resultTextBuilder <<= String.format("An unknown error has been occurred on file: %s  \nAccess Denied\n Stack Trace: \n %s", it.toString, ex.toString());
		}
	}
	
	//returns the new string with the newString instead of the matching pattern
	def replaceText(text) { 
		text.replaceAll(filePattern, newString);
	} 
	
	//Get the extension of the file
	def getExtensionFromFilename(filename) {
		def returned_value = ""
		def m = (filename =~ /(.[^.]*)$/)
		if (m.size()>0) returned_value = ((m[0][0].size()>0) ? m[0][0].substring(1).trim().toLowerCase() : "");
		return returned_value
	}
	
}