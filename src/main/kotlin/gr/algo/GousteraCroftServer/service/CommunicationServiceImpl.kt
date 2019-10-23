package gr.algo.GousteraCroftServer.service




import gr.algo.GousteraCroftServer.GousteraCroftServerApplication
import gr.algo.GousteraCroftServer.context
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.SpringApplication
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowCallbackHandler
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDateTime

@Service
class CommunicationServiceImpl:CommunicationService {


    @Autowired
    @Qualifier("jdbcTemplate1")
    private val sqlite1: JdbcTemplate? = null

    @Autowired
    @Qualifier("jdbcTemplate2")
    private val sqlsrv1: JdbcTemplate? = null


    @Autowired
    @Qualifier("jdbcTemplate3")
    private val sqlite2: JdbcTemplate? = null

    @Autowired
    lateinit var app:GousteraCroftServerApplication




    val comid=2



    override fun AndroidtoAtlantis() {
        fun(){TODO("Parameterise code mask")}


        //////////////////////////////////// SUPPLIER NEW
        sqlite1?.query("SELECT name,afm,phone1,phone2,phone3,address,district,city,id from supplier where erpupd=1 and erpid=0", object: RowCallbackHandler {
            @Throws(SQLException::class)
            override fun processRow(resultSet:ResultSet) {
                while (resultSet.next())
                {

                    var code:String?=sqlsrv1?.queryForObject("SELECT CODE FROM supplier where code like '500%' and comid="+comid.toString()+" order by code desc LIMIT 1", String::class.java)
                    val numpart=code?.substring(5)
                    val cvnumpart=numpart!!.toInt()+1
                    val newcode="120-"+cvnumpart.toString().padStart(4,'0')
                    println("JIM-CODE"+newcode)
                    sqlsrv1?.update("INSERT INTO supplier(name,afm,phone11,phone12,phone21,street1,district1,city1,code,comid,curid,fpastatus) " +
                            "VALUES ('"+resultSet.getString(1)+"','"+resultSet.getString(2)+"','"+resultSet.getString(3)+"','"+resultSet.getString(4)+"','"+
                            resultSet.getString(5)+"','"+resultSet.getString(6)+"','"+resultSet.getString(7)+"','"+
                            resultSet.getString(8)+"','"+newcode+"',"+comid+",1,0)")

                    val oldId=resultSet.getString(9)
                    sqlite2?.update("UPDATE supplier set erpupd=0 where id="+oldId)
                    val newId=sqlsrv1?.queryForObject<String>("select id from supplier where code='"+newcode+"' and comid="+comid)
                    sqlite2?.update("UPDATE supplier set erpid="+newId+" where id="+oldId)
                    sqlite2?.update("UPDATE supplier set id="+newId+" where id="+oldId)
                    sqlite2?.update("UPDATE croft set supplierid="+newId+" where supplierid="+oldId)






                }
            }
        })



        /////////////////////////??SUPPLIER CHANGE
        sqlite1?.query("SELECT name,afm,phone1,phone2,phone3,address,district,city,erpid from supplier where erpupd=2", object: RowCallbackHandler{
            @Throws(SQLException::class)
            override fun processRow(resultSet:ResultSet) {
                while(resultSet.next()){
                    sqlsrv1?.update("UPDATE supplier SET name='"+ resultSet.getString(1)+"' where id="+resultSet.getString(9))
                    sqlsrv1?.update("UPDATE supplier SET afm='"+ resultSet.getString(2)+"' where id="+resultSet.getString(9))
                    sqlsrv1?.update("UPDATE supplier SET phone11='"+ resultSet.getString(3)+"' where id="+resultSet.getString(9))
                    sqlsrv1?.update("UPDATE supplier SET phone12='"+ resultSet.getString(4)+"' where id="+resultSet.getString(9))
                    sqlsrv1?.update("UPDATE supplier SET phone21='"+ resultSet.getString(5)+"' where id="+resultSet.getString(9))
                    sqlsrv1?.update("UPDATE supplier SET address1="+ resultSet.getString(6)+" where id="+resultSet.getString(9))
                    sqlsrv1?.update("UPDATE supplier SET district1'"+ resultSet.getString(7)+"' where id="+resultSet.getString(9))
                    sqlsrv1?.update("UPDATE supplier SET city1='"+ resultSet.getString(8)+"' where id="+resultSet.getString(9))

                    sqlite2?.update("UPDATE supplier set erpupd=0 where erpid="+resultSet.getString(9)
)


                }
            }
        })

//////////////////////////////////////NEW CROFTS
        sqlite1?.query("SELECT supplierid,iteid,variationid,isbio,gps,village,county,stremma,expectedqty,operatorname,operatorphone,comments,inspectiondate from croft where erpupd=1",
            object: RowCallbackHandler {
                @Throws(SQLException::class)
                override fun processRow(resultSet: ResultSet) {
                    while (resultSet.next()) {
                        val tdate=resultSet.getDate(12).toString()




                        sqlsrv1?.update("INSERT INTO z_croft (supplierid,iteid,variationid,isbio,gps,village,county,stremma,expectedqty,operatorname,operatorphone,comments,inspectiondate) values +" +
                                "("+ resultSet.getInt(1).toString()+"',"+resultSet.getInt(2).toString()+","+ resultSet.getInt(3).toString()+","
                                +resultSet.getInt(4).toString()+",'"+resultSet.getString(5)+"','"+resultSet.getString(6)+"','"
                                +resultSet.getString(7)+"',"+resultSet.getFloat(8).toString()+","+resultSet.getFloat(9).toString()
                                +",'"+resultSet.getString(10)+"','"+resultSet.getString(11)+"','"+tdate+"')")


                        }
                }
            })


/////////////////////////////////////CHANGED CROFTS

        sqlite1?.query("SELECT supplierid,iteid,variationid,isbio,gps,village,county,stremma,expectedqty,operatorname,operatorphone,comments,inspectiondate from croft where erpupd=2",
                object: RowCallbackHandler {
                    @Throws(SQLException::class)
                    override fun processRow(resultSet: ResultSet) {
                        while (resultSet.next()) {
                            val tdate=resultSet.getDate(11).toString()




                            sqlsrv1?.update("UPDATE z_croft (SET supplierid=" + resultSet.getInt(1).toString()+",iteid=,variationid="+ resultSet.getInt(2).toString()+",isbio="
                                    + resultSet.getInt(3).toString()+",gps='"+ resultSet.getString(4)+"',village='"+ resultSet.getString(5)
                                    +"',county='"+ resultSet.getString(6)+"',stremma="+ resultSet.getFloat(7).toString()+",expectedqty="+ resultSet.getFloat(8).toString()+
                                    ",operatorname='"+ resultSet.getString(9)+"',operatorphone='"+resultSet.getString(10)+"',comments='"+ resultSet.getString(11).toString()+"',inspectiondate='"+ tdate+
                                    "' where id="+ resultSet.getInt(13).toString()+"')")


                        }
                    }
                })




    }


    override fun AtlantistoAndroid() {

        sqlite2?.update("VACUUM")


        /////MATERIAL
        val t = sqlite1?.update("DELETE from item")
        println("JIM-"+t.toString())
        sqlsrv1?.query("SELECT codeid,descr from itemgroup where comid="+comid,object: RowCallbackHandler {
            @Throws(SQLException::class)
            override fun processRow(resultSet: ResultSet) {
                while (resultSet.next()) {
                    sqlite2?.update("INSERT INTO item (erpid,description) VALUES ("+resultSet.getString(1)+",'"+resultSet.getString(2)+"')")
                }

            }
        })

        /////VAT
        sqlite2?.update("DELETE from variation")


        sqlsrv1?.query("SELECT codeid,descr from itemgroup3 where comid="+comid,object: RowCallbackHandler {
            @Throws(SQLException::class)
            override fun processRow(resultSet: ResultSet) {
                while (resultSet.next()) {
                    sqlite2?.update("INSERT INTO variation (erpid,description) VALUES ("+resultSet.getInt(1).toString()+",'"+resultSet.getString(2)+"')")
                }

            }
        })



        /// CUSTOMER

        sqlite2?.update("DELETE from supplier")


        sqlsrv1?.query("SELECT id,name,ifnull(afm,''),ifnull(phone11,''),ifnull(phone12,''),ifnull(phone21,''),ifnull(street1,''),ifnull(district1,''),ifnull(city1,'') from supplier where isactive=1 and code like '500%' and comid="+comid,object: RowCallbackHandler {
            @Throws(SQLException::class)
            override fun processRow(resultSet: ResultSet) {
                while (resultSet.next()) {

                    sqlite2?.update("INSERT INTO supplier (erpid,name,afm,phone1,phone2,phone3,address,district,city,erpupd) VALUES ("+resultSet.getInt(1).toString()+
                            ",'" +resultSet?.getString(2)+"','"+resultSet.getString(3)+"','"+resultSet.getString(4)+"','"+resultSet.getString(5)+"','"+
                            resultSet.getString(6)+"','"+resultSet.getString(7)+"','"+resultSet.getString(8)+"','"+resultSet.getString(9)+"',0)")
                }

            }
        })

        sqlite2?.update("DELETE from croft")

        sqlsrv1?.query("SELECT id,supplierid,iteid,variationid,isbio,ifnull(gps,''),ifnull(village,''),ifnull(county,''),ifnull(stremma,0),ifnull(expectedqty,0),ifnull(operatorname,''),ifnull(operatorphone,'')," +
                "ifnull(comments,''),ifnull(inspectiondate,'') from z_croft",object: RowCallbackHandler {
            @Throws(SQLException::class)
            override fun processRow(resultSet: ResultSet) {
                while (resultSet.next()) {

                    sqlite2?.update("INSERT INTO croft (erpid,supplierid,iteid,variationid,isbio,gps,village,county,stremma,expectedqty,operatorname,operatorphone,comments,inspectiondate,erpupd) VALUES ("+
                            resultSet.getInt(1).toString()+","+resultSet.getInt(2).toString()+","+resultSet.getInt(3).toString()+","+resultSet.getInt(4).toString()+","+
                            resultSet.getInt(5).toString()+","+",'" +resultSet.getString(6)+"','"+resultSet.getString(7)+"','"+resultSet.getString(8)+"',"+
                            resultSet.getFloat(9)+","+resultSet.getFloat(10)+",'"+resultSet.getString(11)+"','"+resultSet.getString(12)+"','"+
                            resultSet.getString(13)+"','"+resultSet.getString(14)+"',0)")
                }

            }
        })




        app.restart()
    }






}