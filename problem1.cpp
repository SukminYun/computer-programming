#include <iostream>
#include <string>

const int MAX_STUDENTS = 3;
enum ContestResult
{
    WIN,
    LOSE,
    TIE
};

// The Student class has the following common attributes:
// - name: string
// - social: int (default: 0)
// - energy: int (default: 100)
// However, each student has different attributes according to their type. This is called 'special attribute'
//   Scientist - knowledge: int (default: 0, can be negative)
//   Athlete - health: int (default: 0, can be negative)
//   Artist - creativity: int (default: 0, can be negative)
// These attributes are only accessible by the derived classes and itself.
// They are not accessible by any other function outside the class.
// Implement the getters if needed!
class Student
{
public:
    int social = 0;
    int energy = 100;
    std::string name;
    Student(std::string _name) {this->name = _name;};
    virtual ~Student() {}

    void rest()
    {
        // 1. Resting increases energy by 50
        energy += 50;

        // 2. Display the status of the student
        showStatus();
    }

    void meetFriends()
    {
        // 1. If energy is greater than or equal to 15, increase social by 20 and decrease energy by 15
        if (energy >= 15){
            social += 20;
            energy -= 15;
        }
        // 2. If energy is less than 15, display a message "${StudentName} is too tired to meet friends."
        else {
            std::cout << name << " is too tired to meet friends."<< std::endl;
        }
        // 3. Display the status of the student
        showStatus();
        // *There is no case of overflow
    }

    // There are three types of students: Scientist, Athlete, and Artist
    // Each student has different activities to do and different effects on their stats
    // Override this method in the derived classes
    // Scientist: Study
    //  - If energy is greater than or equal to 15, incerase knowledge by 20 and decrease energy by 15
    //  - If energy is less than 15, display a message "${StudentName} is too tired to study."
    //  - Display the status of the student
    // Athlete: Exercise
    //  - If energy is greater than or equal to 15, increase health by 20 and decrease energy by 15
    //  - If energy is less than 15, display a message "${StudentName} is too tired to exercise."
    //  - Display the status of the student
    // Artist: Work on art
    //  - If energy is greater than or equal to 15, increase creativity by 20 and decrease energy by 15
    //  - If energy is less than 15, display a message "${StudentName} is too tired to work on art."
    //  - Display the status of the student
    // *There is no case of overflow
    virtual void doActivity() = 0;

    // Return the sum of the special attribute and social and energy
    virtual int getStats() = 0;

    // 1. If the result is WIN, increase special attribute by 10
    // 2. If the result is LOSE, decrease energy by 15
    // 3. If the result is TIE, decrease energy by 10
    // 4. Display the status of the student
    // *There is no case of overflow/underflow
    virtual void updateAfterContest(ContestResult result) = 0;

    // Display the status of the student
    // For example, if the name is "John" and its type is Scientist, with knowledge: 0, social: 0, energy: 0,
    // print "Status of John: Knowledge: 0, Social: 0, Energy: 0"
    virtual void showStatus() = 0;
};

class Scientist : public Student{
private:
    int knowledge;
public:
    Scientist(std::string _name) : Student(_name), knowledge(0){}
    void doActivity() {
        if (energy >= 15){
            knowledge += 20;
            energy -= 15;
        }
        else {
            std::cout << name << " is too tired to study." << std::endl;
        }
        std::cout << "Status of " << name << ": Knowledge: " << knowledge << ", Social: " << social << ", Energy: " << energy << std::endl;

    }

    int getStats(){
        return knowledge + social + energy;
    }

    void updateAfterContest(ContestResult result) {
        if (result == WIN){
            knowledge += 10;
        } else if (result == LOSE){
            energy -= 15;
        } else {
            energy -= 10;
        }
        std::cout << "Status of " << name << ": Knowledge: " << knowledge << ", Social: " << social << ", Energy: " << energy << std::endl;
    }
    void showStatus(){
        std::cout << "Status of " << name << ": Knowledge: " << knowledge << ", Social: " << social << ", Energy: " << energy << std::endl;
    }
};

class Athlete : public Student{
private:
    int health;
public:
    Athlete(std::string _name) : Student(_name), health(0){}
    void doActivity() {
        if (energy >= 15){
            health += 20;
            energy -= 15;
        }
        else {
            std::cout << name << " is too tired to exercise." << std::endl;
        }
        std::cout << "Status of " << name << ": Health: " << health << ", Social: " << social << ", Energy: " << energy << std::endl;

    }

    int getStats(){
        return health + social + energy;
    }

    void updateAfterContest(ContestResult result) {
        if (result == WIN){
            health += 10;
        } else if (result == LOSE){
            energy -= 15;
        } else {
            energy -= 10;
        }
        std::cout << "Status of " << name << ": Health: " << health << ", Social: " << social << ", Energy: " << energy << std::endl;
    }
    void showStatus(){
        std::cout << "Status of " << name << ": Health: " << health << ", Social: " << social << ", Energy: " << energy << std::endl;
    }
};

class Artist : public Student{
private:
    int creativity;
public:
    Artist(std::string _name) : Student(_name), creativity(0){}
    void doActivity() {
        if (energy >= 15){
            creativity += 20;
            energy -= 15;
        }
        else {
            std::cout << name << " is too tired to work on art." << std::endl;
        }
        std::cout << "Status of " << name << ": Creativity: " << creativity << ", Social: " << social << ", Energy: " << energy << std::endl;

    }

    int getStats(){
        return creativity + social + energy;
    }

    void updateAfterContest(ContestResult result) {
        if (result == WIN){
            creativity += 10;
        } else if (result == LOSE){
            energy -= 15;
        } else {
            energy -= 10;
        }
        std::cout << "Status of " << name << ": Creativity: " << creativity << ", Social: " << social << ", Energy: " << energy << std::endl;
    }
    void showStatus(){
        std::cout << "Status of " << name << ": Creativity: " << creativity << ", Social: " << social << ", Energy: " << energy << std::endl;
    }
};

class Game
{
private:
    Student *students[MAX_STUDENTS];
    int studentCount;

public:
    Game()
    {
        studentCount = 0;
    }
    ~Game()
    {
    }

    Student *getStudent(std::string name)
    {
        // Return the student whose name is {name}
        // *There is no case where two students have the same name
        // *There is no case where the student with the given name does not exist
        for (int i = 0; i< studentCount; ++i){
            if (students[i]-> name == name){
                return students[i];
            }
        }
        return nullptr;
    }

    void trainStudent()
    {
        int choice = 0;
        std::string name;
        // 1. Ask the user to enter the student name
        std::cout << "Enter student name: ";
        std::cin >> name;
        Student *student = getStudent(name);
        // 2. Display the training menu and ask user to choose an activity
        while (choice!=5){
            std::cout << "--- Training Menu ---\n";
            std::cout << "1. Do Activity\n";
            std::cout << "2. Meet Friends\n";
            std::cout << "3. Rest\n";
            std::cout << "4. Show Status\n";
            std::cout << "5. Exit\n";
            std::cout << "Enter your choice: ";
            std::cin >> choice;
            std::cout << "----------------------\n";
            // 3. Process the chosen activity (refer to example text file)
            switch(choice){
                case 1:
                    student ->doActivity();
                    break;
                case 2:
                    student -> meetFriends();
                    break;
                case 3:
                    student->rest();
                    break;
                case 4:
                    student->showStatus();
                default:
                    break;
            }
            // 4. Repeat until the user chooses to exit(5)
            // *There is no case where the student with the given name does not exist
            // *There is no case where the user enters an invalid choice
        }
    }

    void addStudent()
    {
        std::string type, name;
        // 1. Ask the user to enter the student type and name
        std::cout << "Enter student type (Scientist, Athlete, Artist): ";
        std::cin >> type;
        // 2. Create a new student object according to the given type and add it to the students array
        std::cout << "Enter student name: ";
        std::cin >> name;

        Student *newStudent;
        if (type == "Scientist"){
            newStudent = new Scientist(name);
        }
        else if (type == "Athlete"){
            newStudent = new Athlete(name);
        }
        else if (type == "Artist"){
            newStudent = new Artist(name);
        }
        students[studentCount++] = newStudent;
        // *There is no case where the user enters an invalid student type or the name of an existing student
        // *There is no case where the student count exceeds MAX_STUDENTS
    }

    void contestStudents()
    {
        // 1. Ask the user to enter the names of two students
        std::string name1, name2;
        std::cout << "Enter first student name for the contest: ";
        std::cin >> name1;
        std::cout << "Enter second student name for the contest: ";
        std::cin >> name2;
        std::cout << "Contesting " << name1 << " vs. " << name2 << "\n";
        Student *student1 = getStudent(name1);
        Student *student2 = getStudent(name2);
        // 2. If the energy of any student is less than 15, display a message "${StudentName} is too tired to contest."
        if (student1->energy < 15){
            std::cout << student1->name << " is too tired to contest.\n";
        }
        else if (student2->energy <15){
            std::cout << student2->name << " is too tired to contest.\n";
        }
        // 3. Contest the two students
        else {
            int stats1 = student1->getStats();
            int stats2 = student2->getStats();
            if (stats1 > stats2){
                student1->updateAfterContest(WIN);
                student2->updateAfterContest(LOSE);
                std::cout << student1->name << " wins!\n";
            }
            else if (stats1 < stats2){
                student1->updateAfterContest(LOSE);
                student2->updateAfterContest(WIN);
                std::cout << student2->name << " wins!\n";
            }
            else{
                student1->updateAfterContest(TIE);
                student2->updateAfterContest(TIE);
                std::cout << "It's a tie!\n";
            }
            //  - The student with higher stats wins
            //  - If the stats are equal, it's a tie
            //  - Display the result
            //    - If it is not a tie, display "${StudentName} wins!"
            //    - If it is a tie, display "It's a tie!"
            // 4. Update the stats of the students according to the result
            // *There is no case where the user enters the name of a non-existing student or the same name for both student
            student1->showStatus();
            student2->showStatus();
        }

    }

    void showStatus()
    {
        // Display the status of all students
        for (int i = 0; i < studentCount; i++){
            students[i]->showStatus();
        }
    }

    void run()
    {
        int choice = 0;
        // 1. Display the main menu and ask user to choose an activity
        while (choice != 5) {
            std::cout << "--- Main Menu ---\n";
            std::cout << "1. Add Student\n";
            std::cout << "2. Train Student\n";
            std::cout << "3. Contest Students\n";
            std::cout << "4. Students Status\n";
            std::cout << "5. Exit\n";
            std::cout << "Enter your choice: ";
            std::cin >> choice;
            std::cout << "----------------------\n";
            // 2. Process the chosen activity (refer to example text file)
            switch (choice){
                case 1:
                    addStudent();
                    break;
                case 2:
                    trainStudent();
                    break;
                case 3:
                    contestStudents();
                    break;
                case 4:
                    showStatus();
                    break;
                default:
                break;
            }
            // 3. Repeat until the user chooses to exit(5)
        }
    }
};

int main()
{
    // Good Luck! :)
    Game game;
    game.run();
    return 0;
}