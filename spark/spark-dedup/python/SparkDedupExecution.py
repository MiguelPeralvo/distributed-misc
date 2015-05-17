__author__ = 'MiguelPeralvo'

## Spark Application - execute with spark-submit
import os
import sys
# Create a variable for our root path
SPARK_HOME = os.environ['SPARK_HOME']

# Add the PySpark/py4j to the Python Path
sys.path.insert(0, os.path.join(SPARK_HOME, "python", "build"))
sys.path.insert(0, os.path.join(SPARK_HOME, "python"))

# try the import Spark Modules
try:
    from pyspark import SparkContext
    from pyspark import SparkConf

except ImportError as e:
    print ("Error importing Spark Modules", e)
    sys.exit(1)


## Module Constants


## Closure Functions

## Main functionality

def main(sc, inputPath, outputPath):
    #Get the data in a key value format
    dedupOriginalDataRDD = sc.hadoopFile(inputPath,"org.apache.hadoop.mapred.TextInputFormat", "org.apache.hadoop.io.LongWritable",
      "org.apache.hadoop.io.Text", None, None, None, 1)

    #Get the data in a key value format
    keyValueRDD = dedupOriginalDataRDD.map(lambda t: t[1].split(",")).map(lambda kv: (kv[0], (kv[1], kv[2])))

    #reduce by key so we will only get one record for every primary key
    reducedRDD = keyValueRDD.reduceByKey(lambda a, b: a if a[1] > b[1] else b)

    #Format the data to a human readable format and write it back out to HDFS
    reducedRDD.map(lambda r: str(r[0]) + "," + str(r[1][0]) + "," + str(r[1][1])).saveAsTextFile(outputPath)


if __name__ == "__main__":
    if len(sys.argv)!=3:
        print >> sys.stderr, "Usage: SparkDedupExecution {inputPath} {outputPath}"
        sys.exit(-1)

    inputPath = sys.argv[1]
    outputPath = sys.argv[2]


    APP_NAME = "My Spark Application"

    # Configure Spark
    sparkConf = SparkConf().setAppName("SparkDedupExecution")
    sparkConf = sparkConf.setMaster("local[*]")
    sparkConf.set("spark.cleaner.ttl", "120000")
    sc = SparkContext(conf=sparkConf)

    # Execute Main functionality
    main(sc, inputPath, outputPath)



    sc.stop()