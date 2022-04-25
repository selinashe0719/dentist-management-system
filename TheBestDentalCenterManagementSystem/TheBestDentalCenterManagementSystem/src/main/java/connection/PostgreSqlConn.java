package connection;
import java.sql.*;
import java.util.ArrayList;

import entities.Appointment;
import entities.Branch;
import entities.Employee;
import entities.Patient;
import entities.ProcedureType;
import entities.Record;

public class PostgreSqlConn {

	Connection db = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Statement st = null;
    String sql;

	public void getConn(){
		try {
			Class.forName("org.postgresql.Driver"); 
			db = DriverManager.getConnection("jdbc:postgresql://ec2-44-194-92-192.compute-1.amazonaws.com:5432/deff6muhhqk172?sslmode=require&user=xgfzsbtvsqgrgy&password=dd5e3742b6739564feb62164e011f06815de672c92a203157f1e5a206843a9f7");						
			db.setSchema("DCMSdata");
		}catch(Exception e) {
			System.out.print("error caught");
		}
	}
	
	public void closeDB() {
			try {
				if(rs != null){
					rs.close();
				}
				if(ps != null){
					ps.close();
				}
				if(st != null){
					st.close();
				}
				if(db != null){
					db.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public String[] getReceptionistLoginInfoByName(String username) {
		getConn();
		String[] info = new String[3];
		try {
			ps = db.prepareStatement("SELECT users.id, users.password, users.fname FROM users INNER JOIN employees ON users.id=employees.id WHERE employees.employee_type = 'receptionist' AND users.username = ?;");
			ps.setString(1, username);
			rs = ps.executeQuery();
			while(rs.next()) {
				info[0]=rs.getString(1);
				info[1]=rs.getString(2);
				info[2]=rs.getString(3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return info;
	}
	
	public String[] getDentistLoginInfoByName(String username) {
		getConn();
		String[] info = new String[3];
		try {
			ps = db.prepareStatement("SELECT users.id, users.password, users.fname FROM users INNER JOIN employees ON users.id=employees.id WHERE employees.employee_type = 'dentist' AND users.username = ?;");
			ps.setString(1, username);
			rs = ps.executeQuery();
			while(rs.next()) {
				info[0]=rs.getString(1);
				info[1]=rs.getString(2);
				info[2]=rs.getString(3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return info;
	}
	
	public String[] getPatientLoginInfoByName(String username) {
		getConn();
		String[] info = new String[3];
		try {
			ps = db.prepareStatement("SELECT users.id, users.password, users.fname FROM users INNER JOIN patients ON users.id=patients.id WHERE users.username = ?;");
			ps.setString(1, username);
			rs = ps.executeQuery();
			while(rs.next()) {
				info[0]=rs.getString(1);
				info[1]=rs.getString(2);
				info[2]=rs.getString(3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return info;
	}
	
	public ArrayList<Appointment> getPatientFutureAppointments(String patientId){
		getConn();
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		try {
			ps = db.prepareStatement("SELECT appointment.appointmentid, appointment.adate, appointment.start_time, appointment.end_time, dentist.id, dentist.lname, patient.id, patient.fname, patient.lname, appointment.room, appointment.atype, appointment.status FROM appointment INNER JOIN users dentist ON appointment.employeeid = dentist.id INNER JOIN users patient ON appointment.patientid = patient.id WHERE appointment.patientid = ? AND appointment.status = 'upcoming';");
			ps.setInt(1, Integer.parseInt(patientId));
			rs = ps.executeQuery();
			while(rs.next()) {
				appointments.add(new Appointment(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),"Dr. "+rs.getString(6),rs.getString(7),rs.getString(8)+" "+rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return appointments;
	}
	
	public ArrayList<Appointment> getPatientPastAppointments(String patientId){
		getConn();
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		try {
			ps = db.prepareStatement("SELECT appointment.appointmentid, appointment.adate, appointment.start_time, appointment.end_time, dentist.id, dentist.lname, patient.id, patient.fname, patient.lname, appointment.room, appointment.atype, appointment.status FROM appointment INNER JOIN users dentist ON appointment.employeeid = dentist.id INNER JOIN users patient ON appointment.patientid = patient.id WHERE appointment.patientid = ? AND appointment.status <> 'upcoming';");
			ps.setInt(1, Integer.parseInt(patientId));
			rs = ps.executeQuery();
			while(rs.next()) {
				appointments.add(new Appointment(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),"Dr. "+rs.getString(6),rs.getString(7),rs.getString(8)+" "+rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return appointments;
	}
	
	public ArrayList<Record> getPatientRecords(String patientId){
		getConn();
		ArrayList<Record> records = new ArrayList<Record>();
		try {
			ps = db.prepareStatement("SELECT treatment.treatment_type, treatment.medications, treatment.symptoms, treatment.tooth, treatment.treat_comment, users.lname FROM treatment INNER JOIN users ON treatment.employeeid = users.id WHERE treatment.patientId=?;");
			ps.setInt(1, Integer.parseInt(patientId));
			rs = ps.executeQuery();
			while(rs.next()) {
				records.add(new Record(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),"Dr. "+rs.getString(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return records;
	}
	
	public ArrayList<Patient> getAllPatients(){
		getConn();
		ArrayList<Patient> patients = new ArrayList<Patient>();
		try {
			ps = db.prepareStatement("SELECT users.id, users.fname, users.mname, users.lname, patients.gender, patients.birthday, patients.house_number, patients.street, patients.city, patients.province, users.SSN FROM users INNER JOIN patients ON users.id = patients.id;");
			rs = ps.executeQuery();
			while(rs.next()) {
				patients.add(new Patient(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return patients;
	}
	
	public Patient getPatient(String patientId){
		getConn();
		try {
			ps = db.prepareStatement("SELECT users.id, users.username, users.password, users.fname, users.mname, users.lname, patients.gender, patients.birthday, patients.house_number, patients.street, patients.city, patients.province, users.SSN FROM users INNER JOIN patients ON users.id = patients.id WHERE patients.id=?");
			ps.setInt(1, Integer.parseInt(patientId));
			rs = ps.executeQuery();
			while(rs.next()) {
				return new Patient(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return null;
	}
	
	public ArrayList<Branch> getAllBranches(){
		getConn();
		ArrayList<Branch> branches = new ArrayList<Branch>();
		try {
			ps = db.prepareStatement("SELECT * FROM branch;");
			rs = ps.executeQuery();
			while(rs.next()) {
				branches.add(new Branch(rs.getString(1),rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return branches;
	}
	
	public ArrayList<Employee> getAllEmployees(){
		getConn();
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try {
			ps = db.prepareStatement("SELECT users.id, users.fname, users.mname, users.lname, employees.role, employees.employee_type, employees.house_number, employees.street, employees.city, employees.province, users.ssn, employees.salary, employees.branch FROM users INNER JOIN employees ON users.id = employees.id WHERE employees.employee_type='dentist' ORDER BY users.fname;");
			rs = ps.executeQuery();
			while(rs.next()) {
				employees.add(new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return employees;
	}
	
	public ArrayList<Employee> getEmployeesFromBranch(String branch){
		getConn();
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try {
			ps = db.prepareStatement("SELECT users.id, users.fname, users.mname, users.lname, employees.role, employees.employee_type, employees.house_number, employees.street, employees.city, employees.province, users.ssn, employees.salary, employees.branch FROM users INNER JOIN employees ON users.id = employees.id WHERE employees.branch=? AND employees.employee_type='dentist' ORDER BY users.fname;");
			ps.setString(1,branch);
			rs = ps.executeQuery();
			while(rs.next()) {
				employees.add(new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return employees;
	}
	
	public ArrayList<Appointment> getDentistFutureAppointments(String dentistId){
		getConn();
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		try {
			ps = db.prepareStatement("SELECT appointment.appointmentid, appointment.adate, appointment.start_time, appointment.end_time, dentist.id, dentist.lname, patient.id, patient.fname, patient.lname, appointment.room, appointment.atype, appointment.status FROM appointment INNER JOIN users dentist ON appointment.employeeid = dentist.id INNER JOIN users patient ON appointment.patientid = patient.id WHERE appointment.employeeid = ? AND appointment.status = 'upcoming';");
			ps.setInt(1, Integer.parseInt(dentistId));
			rs = ps.executeQuery();
			while(rs.next()) {
				appointments.add(new Appointment(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),"Dr. "+rs.getString(6),rs.getString(7),rs.getString(8)+" "+rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return appointments;
	}
	
	public ArrayList<ProcedureType> getAllProcedureTypes(){
		getConn();
		ArrayList<ProcedureType> procedureTypes = new ArrayList<ProcedureType>();
		try {
			ps = db.prepareStatement("SELECT * FROM procedure_type;");
			rs = ps.executeQuery();
			while(rs.next()) {
				procedureTypes.add(new ProcedureType(rs.getString(1),rs.getString(2),rs.getString(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return procedureTypes;
	}
	
	public void addNewPatientRecord(String type, String medications, String symptoms, String tooth, String comments, String patientId, String employeeid) {
		getConn();
		try {
			ps = db.prepareStatement("INSERT INTO treatment (treatment_type, medications, symptoms, tooth, treat_comment, patientid, employeeid) VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, type);
			ps.setString(2, medications);
			ps.setString(3, symptoms);
			ps.setInt(4, Integer.parseInt(tooth));
			ps.setString(5, comments);
			ps.setInt(6, Integer.parseInt(patientId));
			ps.setInt(7, Integer.parseInt(employeeid));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	
	public ArrayList<Appointment> getAllAppointments(){
		getConn();
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		try {
			ps = db.prepareStatement("SELECT appointment.appointmentid, appointment.adate, appointment.start_time, appointment.end_time, dentist.id, dentist.lname, patient.id, patient.fname, patient.lname, appointment.room, appointment.atype, appointment.status FROM appointment INNER JOIN users dentist ON appointment.employeeid = dentist.id INNER JOIN users patient ON appointment.patientid = patient.id;");
			rs = ps.executeQuery();
			while(rs.next()) {
				appointments.add(new Appointment(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),"Dr. "+rs.getString(6),rs.getString(7),rs.getString(8)+" "+rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return appointments;
	}
	
	public void addNewPatient(String username, String password, String fname, String mname, String lname, String ssn, String gender, String birthday, String houseNumber, String street, String city, String province) {
		getConn();
		try {
			ps = db.prepareStatement("INSERT INTO users (username, password, fname, mname, lname, ssn) VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, fname);
			ps.setString(4, mname);
			ps.setString(5, lname);
			ps.setString(6, ssn);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			Integer id;
			if (rs.next()) {
				id = rs.getInt(1);
				ps = db.prepareStatement("INSERT INTO patients (id, gender, birthday, house_number, street, city, province) VALUES (?, ?, ?, ?, ?, ?, ?)");
				ps.setInt(1, id);
				ps.setString(2, gender);
				ps.setDate(3, Date.valueOf(birthday));
				ps.setInt(4, Integer.valueOf(houseNumber));
				ps.setString(5, street);
				ps.setString(6, city);
				ps.setString(7, province);
				ps.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	
	public void updatePatient(String id, String username, String password, String fname, String mname, String lname, String ssn, String gender, String birthday, String houseNumber, String street, String city, String province) {
		getConn();
		try {
			ps = db.prepareStatement("UPDATE users SET username=?, password=?, fname=?, mname=?, lname=?, ssn=? WHERE id=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, fname);
			ps.setString(4, mname);
			ps.setString(5, lname);
			ps.setString(6, ssn);
			ps.setInt(7, Integer.parseInt(id));
			ps.executeUpdate();
			
			ps = db.prepareStatement("UPDATE patients SET gender=?, birthday=?, house_number=?, street=?, city=?, province=? WHERE id=?");
			ps.setString(1, gender);
			ps.setDate(2, Date.valueOf(birthday));
			ps.setInt(3, Integer.valueOf(houseNumber));
			ps.setString(4, street);
			ps.setString(5, city);
			ps.setString(6, province);
			ps.setInt(7, Integer.parseInt(id));
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	
	public Employee getDentist(String employeeId){
		getConn();
		try {
			ps = db.prepareStatement("SELECT users.id, users.username, users.password, users.fname, users.mname, users.lname, employees.role, employees.employee_type, employees.house_number, employees.street, employees.city, employees.province, users.SSN, employees.salary, employees.branch FROM users INNER JOIN employees ON users.id = employees.id WHERE employees.id=?");
			ps.setInt(1, Integer.parseInt(employeeId));
			rs = ps.executeQuery();
			while(rs.next()) {
				return new Employee(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return null;
	}
	
	public void addNewEmployee(String username, String password, String fname, String mname, String lname, String ssn, String role, String salary, String houseNumber, String street, String city, String province, String branch) {
		getConn();
		try {
			ps = db.prepareStatement("INSERT INTO users (username, password, fname, mname, lname, ssn) VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, fname);
			ps.setString(4, mname);
			ps.setString(5, lname);
			ps.setString(6, ssn);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			Integer id;
			if (rs.next()) {
				id = rs.getInt(1);
				ps = db.prepareStatement("INSERT INTO employees (id, role, employee_type, salary, house_number, street, city, province, branch) VALUES (?, ?, 'dentist', ?, ?, ?, ?, ?, ?)");
				ps.setInt(1, id);
				ps.setString(2, role);
				ps.setInt(3, Integer.parseInt(salary));
				ps.setInt(4, Integer.valueOf(houseNumber));
				ps.setString(5, street);
				ps.setString(6, city);
				ps.setString(7, province);
				ps.setString(8, branch);
				ps.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	
	public void updateEmployee(String id, String username, String password, String fname, String mname, String lname, String ssn, String role, String salary, String houseNumber, String street, String city, String province, String branch) {
		getConn();
		try {
			ps = db.prepareStatement("UPDATE users SET username=?, password=?, fname=?, mname=?, lname=?, ssn=? WHERE id=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, fname);
			ps.setString(4, mname);
			ps.setString(5, lname);
			ps.setString(6, ssn);
			ps.setInt(7, Integer.parseInt(id));
			ps.executeUpdate();
			
			ps = db.prepareStatement("UPDATE employees SET role=?, salary=?, house_number=?, street=?, city=?, province=?, branch=? WHERE id=?");
			ps.setString(1, role);
			ps.setInt(2, Integer.parseInt(salary));
			ps.setInt(3, Integer.valueOf(houseNumber));
			ps.setString(4, street);
			ps.setString(5, city);
			ps.setString(6, province);
			ps.setString(7, branch);
			ps.setInt(8, Integer.parseInt(id));
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
	}
	
	public Appointment getAppointment(String appointmentId){
		getConn();
		try {
			ps = db.prepareStatement("SELECT appointment.appointmentid, appointment.adate, appointment.start_time, appointment.end_time, dentist.id, dentist.lname, patient.id, patient.fname, patient.lname, appointment.room, appointment.atype, appointment.status FROM appointment INNER JOIN users dentist ON appointment.employeeid = dentist.id INNER JOIN users patient ON appointment.patientid = patient.id WHERE appointment.appointmentid=?;");
			ps.setInt(1, Integer.parseInt(appointmentId));
			rs = ps.executeQuery();
			while(rs.next()) {
				return new Appointment(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),"Dr. "+rs.getString(6),rs.getString(7),rs.getString(8)+" "+rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return null;
	}
	
	public void addNewAppointment(String patientId, String dentistId, String date, String starttime, String endtime, String type, String status, String room){
		getConn();
		try {
			ps = db.prepareStatement("INSERT INTO appointment (adate, start_time, end_time, atype, status, room, patientid, employeeid) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
			ps.setDate(1, Date.valueOf(date));
			ps.setTime(2, Time.valueOf(starttime+":00"));
			ps.setTime(3, Time.valueOf(endtime+":00"));
			ps.setString(4, type);
			ps.setString(5, status);
			ps.setInt(6, Integer.parseInt(room));
			ps.setInt(7, Integer.parseInt(patientId));
			ps.setInt(8, Integer.parseInt(dentistId));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	
	public void updateAppointment(String id, String patientId, String dentistId, String date, String starttime, String endtime, String type, String status, String room){
		getConn();
		try {
			ps = db.prepareStatement("UPDATE appointment SET adate=?, start_time=?, end_time=?, atype=?, status=?, room=?, patientid=?, employeeid=? WHERE appointmentid=?;");
			ps.setDate(1, Date.valueOf(date));
			ps.setTime(2, Time.valueOf(starttime+":00"));
			ps.setTime(3, Time.valueOf(endtime+":00"));
			ps.setString(4, type);
			ps.setString(5, status);
			ps.setInt(6, Integer.parseInt(room));
			ps.setInt(7, Integer.parseInt(patientId));
			ps.setInt(8, Integer.parseInt(dentistId));
			ps.setInt(9, Integer.parseInt(id));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
}
