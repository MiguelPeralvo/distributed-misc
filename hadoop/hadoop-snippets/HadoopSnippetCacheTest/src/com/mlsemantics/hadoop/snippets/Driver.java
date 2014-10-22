package com.mlsemantics.hadoop.snippets;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Code Snippet for basic play.
 *
 * @author Miguel Peralvo
 * @category Hadoop Snippets
 * @version 0.2
 * @Date 15 Dec 2013
 */
public class Driver extends Configured implements Tool {

	// Main method
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new Configuration(), new Driver(),
				args);
		// Exits with a a status update.
		System.exit(exitCode);
	}

	@Override
	public int run(String[] args) throws Exception {
		boolean success = false;

		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();

		final String NAME_NODE = conf.get("fs.default.name");

		final String CACHE_FILE = "./cache/cache.dat";

		if (NAME_NODE != null) {
			DistributedCache.addCacheFile(new URI(NAME_NODE
					+ CACHE_FILE), conf);
		}

		Job job = new Job(conf, "Cache");
		job.setJarByClass(Driver.class);

		// Output data formats for MapperCacheTest
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		// Final Output data formats (output of reducer)
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		// Specifying the MapperCacheTest and ReducerCacheTest Class
		job.setMapperClass(MapperCacheTest.class);
		job.setReducerClass(ReducerCacheTest.class);

		// Specifying the paths for input and output dirs
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

		// Submit the job
		success = job.waitForCompletion(true);

		return success ? 0 : 1;
	}
}