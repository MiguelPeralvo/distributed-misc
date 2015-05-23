package org.sia.chapter03App

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext

/**
 * @author ${user.name}
 */
object GHPushCounter {

  def main(args : Array[String]) {
    val conf = new SparkConf()
      .setAppName("GitHub push counter")
      .setMaster("local[4]")

    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    
    val homeDir = System.getenv("HOME")
    val ghLog = sqlContext.jsonFile(homeDir + "/Git/SparkInAction/github-archive/*.json")
    //val ghLog = sqlContext.load(homeDir + "/sia/github-archive/*.json", "json")
    //val ghFile = sqlContext.load("2015-03-01-0.json", "json")

    val pushes = ghLog.filter("type = 'PushEvent'") 
    val grouped = pushes.groupBy("actor.login").count
    val ordered = grouped.orderBy(grouped("count").desc)

    
    val empLogins = sqlContext.load(homeDir + "/Git/SparkInAction/first-edition/ch03/ghEmployees.json", 
        "json")
    import pushes.sqlContext.implicits._
    
    val joined = ordered
      .join(empLogins, $"login" === $"username", "inner")
      .orderBy($"count".desc)
      .select("login", "count")
      
    joined.show()
    
    
  }

}
