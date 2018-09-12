package com.example.smitlimbani.noticeme5253;

public class Org_users_type {

        private Boolean student;

        private Boolean faculty;

        public Boolean getStudent() {
            return student;
        }

        public void setStudent(Boolean student) {
            this.student = student;
        }

        public Boolean getFaculty() {
            return faculty;
        }

        public void setFaculty(Boolean faculty) {
            this.faculty = faculty;
        }

    @Override
        public String toString()
        {
            return "ClassPojo [student = "+student+", faculty = "+faculty+"]";
        }
}

