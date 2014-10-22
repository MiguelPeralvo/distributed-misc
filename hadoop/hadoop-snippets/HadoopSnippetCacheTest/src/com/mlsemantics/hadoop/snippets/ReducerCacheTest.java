package com.mlsemantics.hadoop.snippets;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * ReducerCacheTest Class
 * @author Miguel Peralvo
 * @category Hadoop Snippets
 * @version 0.2
 * @Date 15 Dec 2013
 */
public class ReducerCacheTest extends Reducer<Text, Text, Text, Text> {

	/*
	 * Doing nothing in our example
	 */
	protected void reduce(
			Text arg0,
			java.lang.Iterable<Text> arg1,
			org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text>.Context arg2)
			throws java.io.IOException, InterruptedException {
	};
}