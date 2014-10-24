import csv
import sys
import StringIO
from pyspark import SparkContext


def writeRecords(lines):
    """Write out CSV lines"""
    output = StringIO.StringIO()
    writer = csv.DictWriter(output, fieldnames=["name", "fieldToRead"])
    for record in lines:
        writer.writerow(record)
    return [output.getvalue()]

def loadLine(line):
    """Parse a CSV line"""
    input = StringIO.StringIO(line)
    reader = csv.DictReader(input, fieldnames=["name", "fieldToRead"])
    return reader.next()

def loadLines(fileNameContents):
    """Load all the records in a given file"""
    input = StringIO.StringIO(fileNameContents[1])
    reader = csv.DictReader(input, fieldnames=["name", "fieldToRead"])
    return reader


if __name__ == "__main__":
    if len(sys.argv) != 4:
        print "Error syntax: ReadCsvFile [sparkmaster] [inputfile] [outputfile]"
        sys.exit(-1)
    master = sys.argv[1]
    inputFile = sys.argv[2]
    outputFile = sys.argv[3]
    sc = SparkContext(master, "ReadCsvFile")

    # Partial Approach
    input = sc.textFile(inputFile)
    data = input.map(loadLine)
    filteredValues = data.filter(lambda x: x['fieldToRead'] == "value")
    filteredValues.mapPartitions(writeRecords).saveAsTextFile(outputFile)

    # Complete Approach
    fullFileData = sc.wholeTextFiles(inputFile).flatMap(loadLines)
    fullFileFilteredValues = fullFileData.filter(lambda x: x['fieldToRead'] == "value")
    fullFileFilteredValues.mapPartitions(writeRecords).saveAsTextFile(outputFile+"complete")
    sc.stop()
    print "Completed"
