package models

import scalikejdbc._
import scalikejdbc.SQLInterpolation._

case class User(id: Long, email: String, password: String, fullname: String, isAdmin: Boolean)

object User {

  private val * = (rs: WrappedResultSet) => User(
    id = rs.long("id"),
    email = rs.string("email"),
    password = rs.string("password"),
    fullname = rs.string("fullname"),
    isAdmin = rs.boolean("isAdmin")
  )

  def findByEmail(email: String)(implicit session: DBSession = AutoSession): Option[User] = {
    sql"SELECT * FROM user WHERE email = ${email}".map(*).single.apply()
  }

  def findById(id: Long)(implicit session: DBSession = AutoSession): Option[User] = {
    sql"SELECT * FROM user WHERE id = ${id}".map(*).single.apply()
  }

  def authenticate(email: String, password: String): Option[User] = {
    findByEmail(email).filter(_.password == password)
  }

}