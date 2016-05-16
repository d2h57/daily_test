package com.dy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class SqliteOperation {
	static class City{
		private String id;
		private String spell;
		private String city;
		private String area;
		private String prov;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getProv() {
			return prov;
		}
		public void setProv(String prov) {
			this.prov = prov;
		}
		public String getSpell() {
			return spell;
		}
		public void setSpell(String spell) {
			this.spell = spell;
		}
	}
	
	public static void main(String[] args) throws Exception {
		Connection c = null;
		Statement stmt = null;
		PreparedStatement preStmt = null;
		try {
			List<City> citys = new LinkedList<City>();
			BufferedReader reader = new BufferedReader(new FileReader(new File("f:\\city")));
			String line = null;
			while((line = reader.readLine()) != null){
				City city = new City();
				int beginIndex = line.indexOf("<td>");
				int endIndex = line.indexOf("</td>", beginIndex);
				String content = line.substring(beginIndex+4,endIndex);
				city.setId(content);
				
				beginIndex = line.indexOf("<td>",endIndex);
				endIndex = line.indexOf("</td>",beginIndex);
				content = line.substring(beginIndex+4,endIndex);
				city.setSpell(content);
				
				beginIndex = line.indexOf("<td>",endIndex);
				endIndex = line.indexOf("</td>",beginIndex);
				content = line.substring(beginIndex+4,endIndex);
				city.setCity(content);
				
				beginIndex = line.indexOf("<td>",endIndex);
				endIndex = line.indexOf("</td>",beginIndex);
				content = line.substring(beginIndex+4,endIndex);
				city.setArea(content);
				
				beginIndex = line.indexOf("<td>",endIndex);
				endIndex = line.indexOf("</td>",beginIndex);
				content = line.substring(beginIndex+4,endIndex);
				city.setProv(content);
				
				citys.add(city);
			}
			
			reader.close();
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:security.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "CREATE TABLE city "
					+ "(ID varchar(16) PRIMARY KEY     NOT NULL,"
					+ " spell varchar(16) NOT NULL, "
					+ " city varchar(16) NOT NULL, "
					+ " area varchar(16) NOT NULL, "
					+ " prov varchar(16) not null)";
			stmt.executeUpdate(sql);
			stmt.close();

			preStmt = c
					.prepareStatement("INSERT INTO city(ID,spell,city,area,prov) values(?,?,?,?,?)");

			for (City city : citys) {
				preStmt.setString(1, city.getId());
				preStmt.setString(2, city.getSpell());
				preStmt.setString(3, city.getCity());
				preStmt.setString(4, city.getArea());
				preStmt.setString(5, city.getProv());

				preStmt.addBatch();
			}

			preStmt.executeBatch();
			preStmt.close();

			c.commit();
			c.close();
			System.out.println("Records created successfully");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != c && !c.isClosed()) {
				if (null != stmt) {
					stmt.close();
				}
				if (null != preStmt) {
					preStmt.close();
				}
				c.close();
			}
		}
	}
}
