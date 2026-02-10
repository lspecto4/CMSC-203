/*
 * Class: CMSC-203-30295
 * Instructor: Grinberg
 * Description: Program that calculates student's final grade.
 * Due: 2/9/2026
 * Platform/compiler: Eclipse
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or any source. I have not given my code to any student.
 * Print your Name here: Laurie Spector
*/

import java.util.Scanner;
import java.io.*;

public class GradeCalculator
{
	public static void main(String[] args) throws IOException
	{

		// Display program header.
		System.out.println("========================================");
		System.out.println("   CMSC203 Project 1 - Grade Calculator ");
		System.out.println("========================================\n");
		
		// Establishes default input and output file names; renames if user has entered command line arguments.
		String inputFileName = "grades_input.txt";
		String outputFileName = "grades_report.txt";
		if (args.length == 1) // if user entered one argument, use it for input file name
			inputFileName = args[0];
		if (args.length == 2) // if user entered two arguments, use them for input and output file names
			{
				inputFileName = args[0];
				outputFileName = args[1];
			}
		
		System.out.println("Using input file: " + inputFileName);
		System.out.println("Using output file: " + outputFileName);
		
		// Creates Scanner and File objects to read input file
		Scanner inputFile = new Scanner(new File(inputFileName));
		
		// Creates File object for config file
		File file = new File ("gradeconfig.txt");
		
		// Creates PrintWriter object to write to output file.
		PrintWriter outputFile = new PrintWriter(outputFileName);
		
		// Sets flag for whether default config is used
		boolean useDefaultConfig = false;
		
		// If config file exists, verify whether scores add up to 100. 
		// If scores do not equal 100 or config file doesn't exist, sets flag to use default config.
		if (file.exists())
		{
			int totalScore = 0;
			Scanner configFile = new Scanner(file); // creates Scanner object to read config file
			configFile.nextLine(); // consume first line
			int categories = configFile.nextInt();
			configFile.nextLine(); // consume whitespace
			for (int i = 0; i < categories; i++)
			{
				configFile.next(); // consume category name
				int score = configFile.nextInt();
				totalScore += score;
			}
			if (totalScore != 100)
				useDefaultConfig = true;
			configFile.close();
		}
		else
		{
			useDefaultConfig = false;
		}
		
		
		// Calculates overall average using config file.
		double overallAverage = 0;
		if (!useDefaultConfig)
		{
			System.out.println("Using configuration file: gradeconfig.txt\n");
			outputFile.println("Using configuration file: gradeconfig.txt\n");
			Scanner configFile = new Scanner(file); // creates Scanner object to read config file
			while (configFile.hasNext()) // Makes sure the entire config file is used
			{
				String courseName = configFile.nextLine();
				int gradedCategories = configFile.nextInt();
				configFile.nextLine(); // consumes whitespace
				
				String firstName = inputFile.nextLine();
				String lastName = inputFile.nextLine();
				System.out.println("Student: " + firstName + " " + lastName);
				outputFile.println("Student: " + firstName + " " + lastName);
				System.out.println("Course: " + courseName);
				outputFile.println("Course: " + courseName);
				System.out.println("\nCategory Results:");
				outputFile.println("\nCategory Results:");
				
				for (int i = 0; i < gradedCategories; i++) // Iterates for every graded category
				{
					String configCategoryName = configFile.next();
					String inputCategoryName = inputFile.next();
					if (configCategoryName.equals(inputCategoryName))
					{
						double categoryWeight = configFile.nextDouble();
						int numberOfScores = inputFile.nextInt();
						double score, scoreSum = 0.0;
						for (int j = 0; j < numberOfScores; j++) // Iterates for every score within a category
						{
							score = inputFile.nextDouble();
							scoreSum += score;
						}
						double categoryAverage = scoreSum / numberOfScores;
						System.out.print("  " + configCategoryName + " (" + (int)categoryWeight + "%): average = ");
						System.out.printf("%.2f\n", categoryAverage);
						outputFile.print("  " + configCategoryName + " (" + (int)categoryWeight + "%): average = ");
						outputFile.printf("%.2f\n", categoryAverage);
						double weightedCategory = (categoryAverage * categoryWeight)/100;
						overallAverage += weightedCategory;
					}
					else
					{
						int numberOfScores = inputFile.nextInt();
						configFile.nextDouble(); // consume value in invalid category
						for (int j = 1; j <= numberOfScores; j++)
						{
							inputFile.nextDouble(); // consume scores in invalid category
						}
						System.out.println("  Category names don't match. Skipping to next category...");
						outputFile.println("  Category names don't match. Skipping to next category...");
					}
				}
			}
			configFile.close();
			inputFile.close();
		}
		// Calculates overall average using default config.
		else
		{
			System.out.println("Using default configuration...\n");
			outputFile.println("Using default configuration.\n");
			String courseName = "CMSC203 Computer Science I";
			int gradedCategories = 3;
			String firstName = inputFile.nextLine();
			String lastName = inputFile.nextLine();
			System.out.println("Student: " + firstName + " " + lastName);
			outputFile.println("Student: " + firstName + " " + lastName);
			System.out.println("Course: " + courseName);
			outputFile.println("Course: " + courseName);
			System.out.println("\nCategory Results:");
			outputFile.println("\nCategory Results:");
			
			for (int i = 0; i < gradedCategories; i++) // Iterates for every graded category
			{
				String configCategoryName = "";
				double categoryWeight = 0;
				
				if (i == 0)
				{
					configCategoryName = "Projects";
					categoryWeight = 40.0;
				}
				else if (i == 1)
				{
					configCategoryName = "Quizzes";
					categoryWeight = 30.0;
				}
				else if (i == 2)
				{
					configCategoryName = "Exams";
					categoryWeight = 30.0;
				}
					
				String inputCategoryName = inputFile.next();
				if (configCategoryName.equals(inputCategoryName))
				{
					int numberOfScores = inputFile.nextInt();
					double score, scoreSum = 0.0;
					for (int j = 0; j < numberOfScores; j++) // Iterates for every score within a category
					{
						score = inputFile.nextDouble();
						scoreSum += score;
					}
					double categoryAverage = scoreSum / numberOfScores;
					System.out.print("  " + configCategoryName + " (" + (int)categoryWeight + "%): average = ");
					System.out.printf("%.2f\n", categoryAverage);
					outputFile.print("  " + configCategoryName + " (" + (int)categoryWeight + "%): average = ");
					outputFile.printf("%.2f\n", categoryAverage);
					double weightedCategory = (categoryAverage * categoryWeight)/100;
					overallAverage += weightedCategory;
				}
				else
				{
					int numberOfScores = inputFile.nextInt();
					for (int j = 1; j <= numberOfScores; j++)
					{
						inputFile.nextDouble(); // consume scores in invalid category
					}
					System.out.println("  Category names don't match. Skipping to next category...");
					outputFile.println("  Category names don't match. Skipping to next category...");
				}
			}
			inputFile.close();
		}
		
		
		// Determines grading format.
		System.out.println("\nApply +/- grading? (Y/N): ");
		Scanner keyboard = new Scanner(System.in);
		String keyboardInput = keyboard.nextLine();
		char plusMinusGrading = keyboardInput.charAt(0);
		while (plusMinusGrading != 'y' && plusMinusGrading != 'Y' && plusMinusGrading != 'n' && plusMinusGrading != 'N')
		{
			System.out.print("\nApply +/- grading? (Y/N): ");
			keyboardInput = keyboard.next();
			plusMinusGrading = keyboardInput.charAt(0);
		}
		
		
		
		// Determines base grade regardless of grading format.
		String baseGrade = "";
		String finalGrade = "";
		if (overallAverage >= 90 && overallAverage <= 100)
		{
			baseGrade = "A";
		}
		else if (overallAverage >= 80 && overallAverage <= 89.99)
		{
			baseGrade = "B";
		}
		else if (overallAverage >= 70 && overallAverage <= 79.99)
		{
			baseGrade = "C";
		}
		else if (overallAverage >= 60 && overallAverage <= 69.99)
		{
			baseGrade = "D";
		}
		else if (overallAverage < 60)
		{
			baseGrade = "F";
		}
		
		
		// Determines final grade based on user's format selection.
		if (plusMinusGrading == 'Y' || plusMinusGrading == 'y')
		{
			switch (baseGrade)
			{
			case "A":
				if (overallAverage >= 98)
					finalGrade = "A+";
				else if (overallAverage <= 92)
					finalGrade = "A-";
				else
					finalGrade = "A";
				break;
			case "B":
				if (overallAverage >= 88)
					finalGrade = "B+";
				else if (overallAverage <= 82)
					finalGrade = "B-";
				else
					finalGrade = "B";
				break;
			case "C":
				if (overallAverage >= 78)
					finalGrade = "C+";
				else if (overallAverage <= 72)
					finalGrade = "C-";
				else
					finalGrade = "C";
				break;
			case "D":
				if (overallAverage >= 68)
					finalGrade = "D+";
				else if (overallAverage <= 62)
					finalGrade = "D-";
				else
					finalGrade = "D";
				break;
			case "F":
				finalGrade = "F";
			}
		}
		else
			finalGrade = baseGrade;
		
		
		// Displays grade to user.
		System.out.printf("Overall numeric average: %.2f\n", overallAverage);
		outputFile.printf("\nOverall numeric average: %.2f\n", overallAverage);
		System.out.println("Base letter grade: " + baseGrade);
		outputFile.println("Base letter grade: " + baseGrade);
		System.out.println("Final letter grade: " + finalGrade);
		outputFile.println("Final letter grade: " + finalGrade);
		System.out.println("\nProgrammed by: Laurie Spector");
		outputFile.println("\nProgrammed by: Laurie Spector");
		outputFile.close();
		
	} 
}