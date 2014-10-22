package com.vrdthoughts.research.hadoop;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Mapper Class
 *
 * @author Varad Meru, Orzota, Inc.
 * @URL http://vrdthoughts.com
 * @category Hadoop Code Snippets
 * @version 0.1
 * @Date 04 Apr 2013
 */
public class MapperClass extends Mapper<LongWritable, Text, Text, Text> {
	protected Configuration conf;

	public MapperClass() {
		conf = new Configuration();
	}

	@Override
	protected void setup(
			org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text>.Context context)
			throws java.io.IOException, InterruptedException {

		// Fetching the cache files
		Path[] paths = DistributedCache.getLocalCacheFiles(context
				.getConfiguration());

		BufferedReader bufferedReader = null;

		/*
		 * Print objects from Cache file
		 */
		for (int i = 0; i < paths.length; i++) {

			// Matching the name of the file ("cache"). As the cache file can
			// also be a directory, and thus contain multiple files
			if (paths[i].toString().contains("cache")) {

				String record = null;
				String records = "";

				bufferedReader = new BufferedReader(new FileReader(
						paths[i].toString()));

				// Reading all the records into a single 'records' string.
				while ((record = bufferedReader.readLine()) != null) {
					records += record;
				}

				// Printing (for reference only). The output would be located in
				// the userLogs dir, under the job../attempt../stdout
				System.out.println("Records :" + records);
			}
		}
	}

	/*
	 * Doing nothing in our example
	 */
	@Override
	protected void map(
			LongWritable key,
			Text value,
			org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text>.Context context)
			throws java.io.IOException, InterruptedException {

	}
}