package com.sbux.cust.mgmt.resources


import java.io.{File, FileInputStream, InputStream}
import java.security.{KeyStore, SecureRandom}
import javax.net.ssl.{SSLContext, TrustManagerFactory}

import com.datastax.driver.core.{Cluster, NettySSLOptions}
import com.datastax.driver.core.querybuilder.QueryBuilder
import com.datastax.driver.core.querybuilder.QueryBuilder._
import com.sbux.cust.mgmt.entities.Customer
import io.netty.handler.ssl.{SslContextBuilder, SslProvider}
//import sun.security.ssl.ProtocolVersion
import com.datastax.driver.core.Cluster
import com.datastax.driver.core.ResultSet
import com.datastax.driver.core.Row
import com.datastax.driver.core.SSLOptions
import com.datastax.driver.core.Session
import com.google.common.base.Optional

import scala.concurrent.{Await, Future}
/**
  * Created by inbanerj on 10/28/16.
  */
class CassandraResource(session: com.datastax.driver.core.Session) {

  //import scala.concurrent.ExecutionContext.Implicits.global

  // implicit val cache = new SessionQueryCache[PlainConverter](session)

  // class for binding input/output parameters
  case class User(userId: Int, name: String)


  def doDbActivity(): Unit = {
    session.execute("CREATE TABLE IF NOT EXISTS things (id int, name text, PRIMARY KEY (id))")
    session.execute("INSERT INTO things (id, name) VALUES (1, 'foo');")

    println("Table is ready for the Qury")

    val selectStmt = select().column("name")
      .from("things")
      .where(QueryBuilder.eq("id", 1))
      .limit(1)

    val resultSet = session.execute(selectStmt)
    val row = resultSet.one()
    //row.getString("name") should be("foo")

    println("Selected Row: " + row)

    session.execute("DROP TABLE things;")
    println("Drop Table")

  }


}

object  CassandraResource{
  def getSSLOptions: SSLOptions = {
    val ks : KeyStore= KeyStore.getInstance("JKS")
    // make sure you close this stream properly (not shown here for brevity)
    val trustStore : InputStream = new FileInputStream("/Users/inbanerj/cdx/cassandra.truststore")
    ks.load(trustStore, "Sbux1Sbux1".toCharArray())
    val tmf : TrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    tmf.init(ks)

    val  builder : SslContextBuilder = SslContextBuilder
      .forClient()
      .sslProvider(SslProvider.OPENSSL)
      .trustManager(tmf) // only if you use client authentication
      .keyManager(new File("client.crt"), new File("client.key"))

    new NettySSLOptions(builder.build())
  }

  def execute(customer : Customer) : Unit = {
    //def main(array : Array[String]) : Unit = {

    val cluster = Cluster.builder()
      .addContactPoint("40.112.187.8")
      .withCredentials("cassandra","cassandra")
      .withClusterName("'Test Cluster")
      //.withSSL(this.getSSLOptions)
        .withSSL()
      .build()
    val session = cluster.connect("dconway")

    val ccConnector = new CassandraResource(session)

    //    Await.result(ccConnector.insertUser(ccConnector.User(9, "John")), 10 seconds)
    //
    //    val users = Await.result(ccConnector.selectAllUsers, 10 seconds)

    //ccConnector.doDbActivity()

    println("Existing program")
  }
}

