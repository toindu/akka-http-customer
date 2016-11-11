package com.sbux.resource

import com.sbux.quiz.mgmt.entities.Customer
import com.sbux.quiz.mgmt.resources.CassandraResource
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable


/**
  * Created by inbanerj on 11/8/16.
  */
class TestCustomCassandra extends FlatSpec with Matchers {

 /* "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new mutable.Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.pop() should be (2)
    stack.pop() should be (1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new mutable.Stack[Int]
    a [NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    }
  }

  "Cassandra Resource" should "connect to execute query" in {
    CassandraResource.execute(Customer("Test1", "I am Indra", 1234))
  }*/
}