#PROVISIONING SCRIPT Start ­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­
#Get the apache yum repo java-1.7.0-openjdk-1.7.0.45-2.4.3.3.el6.x86_64 
#yum install-y wget java-1.7.0-openjdk-devel.x86_64 
yum install -y wget java-1.7.0-openjdk-1.7.0.45-2.4.3.3.el6.x86_64 
yum install -y wget java-1.7.0-openjdk-devel-1.7.0.45-2.4.3.3.el6.x86_64
#get bigtop 
wget -O /etc/yum.repos.d/bigtop.repo http://www.apache.org/dist/bigtop/stable/repos/centos6/bigtop.repo 
#Now install the base components
yum install -y hadoop\* mahout\* hive\* pig\* 
#temp en var for now
sudo sh -c "cat >> .bashrc" <<'EOF' 
export JAVA_HOME=/usr/lib/jvm/java-1.7.0-openjdk-1.7.0.45.x86_64/jre 
EOF
source .bashrc
#init hadoop 
/etc/init.d/hadoop-hdfs-namenode init 

#Start each datanode 
for i in hadoop-hdfs-namenode hadoop-hdfs-datanode ;
	do service $i start ; 
done
#startservices
/usr/lib/hadoop/libexec/init-hdfs.sh
service hadoop-yarn-resourcemanager start
service hadoop-yarn-nodemanager start
hadoop fs -ls -R /
# Make a directory so that vagrant user has a dir to run jobs inside of. 
sudo -u hdfs hadoop fs -mkdir /user/vagrant
sudo -u hdfs hadoop fs -chown vagrant /user/vagrant
#get stuff
#wget http://apache.cbox.biz/hadoop/common/stable2/hadoop-2.2.0.tar.gz
#wget http://apache.cbox.biz/hive/hive-0.12.0/hive-0.12.0.tar.gz 
wget ftp://mirror.reverse.net/pub/apache/maven/maven-3/3.1.1/binaries/apache-maven-3.1.1-bin.tar.gz 
#set stuff up
#tar -xvf hadoop-2.2.0.tar.gz
#tar -xvfhive-0.12.0.tar.gz
tar -xvfapache-maven-3.1.1-bin.tar.gz
#mv hive-0.12.0 hive
#mv hadoop-2.2.0 hadoop
mv apache-maven-3.1.1 maven 
#create final env settings centos
sudo sh -c "cat >> .bashrc" <<'EOF'
export MAVEN_HOME=/home/vagrant/maven                                                                                                                                                                                                PATH=$PATH:$JAVA_HOME/bin:$MAVEN_HOME/bin
export PATH   
EOF
# classpath 
source .bashrc