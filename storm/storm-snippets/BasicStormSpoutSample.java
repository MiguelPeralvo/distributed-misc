package com.mlsemantics.storm;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class BasicStormSpoutSample extends BaseRichSpout{
	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector spoutOutputCollector;
	private static final Map<Integer, String> map = new HashMap<Integer, String>();
    	static {
        	map.put(0, "element0");
        	map.put(1, "element1");
        	map.put(2, "element2");
        	map.put(3, "element3");
        	map.put(4, "element4");
            map.put(5, "element5");
            map.put(6, "element6");
            map.put(7, "element7");
            map.put(8, "element8");
            map.put(9, "element9");
    }
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector spoutOutputCollector) {
		// Open the spout
		this.spoutOutputCollector = spoutOutputCollector;
	}
		
	public void nextTuple() {
		// "Iterator" for the storm cluster.
		final Random rand = new Random();
		int randomNumber = rand.nextInt(10);
		spoutOutputCollector.emit(new Values(map.get(randomNumber)));

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("element"));
	}
}

