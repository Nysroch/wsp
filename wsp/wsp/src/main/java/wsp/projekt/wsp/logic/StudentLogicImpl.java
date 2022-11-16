package wsp.projekt.wsp.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import wsp.projekt.wsp.model.QStudent;
import wsp.projekt.wsp.model.Student;
import wsp.projekt.wsp.repository.StudentRepository;

@Service
public class StudentLogicImpl extends BaseLogic<Student> implements StudentLogic{

	private static QStudent student = QStudent.student;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	UniversityLogic iUniversityLogic;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; 
    
	@Override
	public Student get(int studentID) {
		// TODO Auto-generated method stub		
		return query().from(student).where(student.studentID.eq(studentID)).fetchOne();
	}
	
	@Override
	public Student insert(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public List<Student> getList() {
		// TODO Auto-generated method stub
		return query().from(student).fetch();
	}
	
	@Override
	public Integer registerUser(Student student) {
		Integer uniID = iUniversityLogic.getUniversityIdByDomain(student.getEmail().substring(student.getEmail().indexOf("@") + 1));
		student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
		if (uniID != 999) {
			student.setUniversityID(uniID);
			studentRepository.save(student);
			return 200;
		} else {
			return uniID;
		}
	}

	@Override
	public Student findStudentByEmail(String email) throws NotFoundException {
		Student st = query().from(student).where(student.email.eq(email)).fetchOne();
		if (st == null) {
			throw new NotFoundException();
		} else {
			return st;
		}
	}
}
