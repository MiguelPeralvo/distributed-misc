# Spark
FROM spark-base:1.1.0
MAINTAINER anantasty anant.asty@gmail.com

# Instead of using a random port, bind the worker to a specific port
ENV SPARK_WORKER_PORT 8888
EXPOSE 8888

RUN apt-get install -y ipython
RUN apt-get install -y ipython-notebook
RUN apt-get install -y python-numpy python-scipy python-matplotlib
RUN apt-get install -y git
ADD files /root/spark_shell_files

# Add the entrypoint script for the master
ENTRYPOINT ["/root/spark_shell_files/default_cmd"]
