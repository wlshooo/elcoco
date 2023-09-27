#include <GL/glut.h>
#include <GL/gl.h>
#include <GL/glu.h>
GLfloat DeltaX = 0.0, DeltaY = 0.0, C_DeltaX = 0.0025, C_DeltaY = -0.0025;
GLint TopLeftX, TopLeftY, BottomRightX, BottomRightY;
GLfloat Red = 0.0, Green = 0.0, Blue = 0.0, Red_r = 0.0, Green_r = 0.0, Blue_r = 0.0;

void MyDisplay() {
	glClear(GL_COLOR_BUFFER_BIT);
	Red_r = 1.0 + Red;
	Green_r = 0.2 + Green;
	Blue_r = 0.8 + Blue;
	glColor3f(Red_r, Green_r, Blue_r);
	
	glBegin(GL_POLYGON);
	glVertex3f(TopLeftX / 1000.0 + DeltaX, (1000 - TopLeftY) / 1000.0 + DeltaY, 0.0);
	glVertex3f(TopLeftX / 1000.0 + DeltaX, (1000 - BottomRightY) / 1000.0 + DeltaY, 0.0);
	glVertex3f(BottomRightX / 1000.0 + DeltaX, (1000 - BottomRightY) / 1000.0 + DeltaY, 0.0);
	glVertex3f(BottomRightX/ 1000.0 + DeltaX, (1000 - TopLeftY) / 1000.0 + DeltaY, 0.0);
	glEnd();
	glutSwapBuffers();
	//glFlush();
}
void MyMouseClick(GLint button, GLint state, GLint X, GLint Y)
{
	if (button == GLUT_LEFT_BUTTON && state == GLUT_DOWN)
	{
		TopLeftX = X;
		TopLeftY = Y;
	}
}

void MyMouseMove(GLint X, GLint Y)
{
	BottomRightX = X;
	BottomRightY = Y;
	glutPostRedisplay();
}

void MyKeyboard(unsigned char KeyInput, int X, int Y)
{
	switch (KeyInput)
	{
	case 'Q':
		exit(0);  break;
	case 'q':
		exit(0); break;
	case 'P':
		Red = 0.0; Green = 0.0; Blue = 0.0;  break;
	case 'p':
		Red = 0.0; Green = 0.0; Blue = 0.0;  break;
	case 'Y':
		Red = 0.0; Green = 0.8; Blue = -0.8;  break;
	case 'y':
		Red = 0.0; Green = 0.8; Blue = -0.8;  break;
	case 'O':
		Red = 0.0; Green = 0.3; Blue = -0.8;  break;
	case 'o':
		Red = 0.0; Green = 0.3; Blue = -0.8;  break;
	}
	glutPostRedisplay();
}

void MyKeyboardMove(int key, int x, int y)
{
	switch (key)
	{
	case GLUT_KEY_UP:
		C_DeltaX *= 2;
		C_DeltaY *= 2;
		break;
	case GLUT_KEY_DOWN:
		C_DeltaX /= 2;
		C_DeltaY /= 2;
		break;
	
	}
	glutPostRedisplay();
}

void MyMainMenu(int entry)
{
	glutPostRedisplay();
}

void MySubMenu1(int entry)
{
	if (entry == 1)
	{
		TopLeftX = TopLeftX + 50;
		TopLeftY = TopLeftY + 50;
		BottomRightX = BottomRightX - 50;
		BottomRightY = BottomRightY - 50;
	}
	else if (entry == 2)
	{
		TopLeftX = TopLeftX - 50;
		TopLeftY = TopLeftY - 50;
		BottomRightX = BottomRightX + 50;
		BottomRightY = BottomRightY + 50;
	}
	glutPostRedisplay();
}

void MySubMenu2(int entry)
{
	if (entry == 1)
	{
		C_DeltaX = C_DeltaX * 2;
		C_DeltaY = C_DeltaY * 2;
	}
	else if (entry == 2)
	{
		C_DeltaX = C_DeltaX / 2;
		C_DeltaY = C_DeltaY / 2; 
	}
	glutPostRedisplay();
}
void MySubMenu3(int entry)
{
	if (entry == 1)
	{
		Red = 0.0; Green = 0.0; Blue = 0.0;
	}
	else if (entry == 2)
	{
		Red = 0.0; Green = 0.8; Blue = -0.8;
	}
	else if (entry == 3)
	{
		Red = 0.0; Green = 0.3; Blue = -0.8;
	}
	glutPostRedisplay();
}

void MyTimer(int value)
{
	DeltaX = DeltaX + C_DeltaX;
	DeltaY = DeltaY + C_DeltaY;
	if (BottomRightX / 1000.0 + DeltaX >= 1)
		C_DeltaX =- C_DeltaX;
	if (TopLeftX / 1000.0 + DeltaX <= 0)
		C_DeltaX =  - C_DeltaX;
	if ((1000 - TopLeftX) / 1000.0 + DeltaY >= 1)
		C_DeltaY =  - C_DeltaY;
	if ((1000 - BottomRightY) / 1000.0 + DeltaY <= 0)
		C_DeltaY =  - C_DeltaY;

	glutPostRedisplay();
	glutTimerFunc(10, MyTimer, 1);

}


int main(int argc, char** argv)
{
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_RGB | GLUT_DOUBLE);
	glutInitWindowSize(1000, 1000);
	glutInitWindowPosition(0, 0);
	glutCreateWindow("2018305058 이진호");
	glClearColor(0.5, 0.5, 0.5, 1.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(0.0, 1.0, 0.0, 1.0, -1.0, 1.0);

	GLint SubMenu_1 = glutCreateMenu(MySubMenu1);
	glutAddMenuEntry("Small", 1);
	glutAddMenuEntry("Big", 2);

	GLint SubMenu_2 = glutCreateMenu(MySubMenu2);
	glutAddMenuEntry("Up", 1);
	glutAddMenuEntry("Down", 2);

	GLint SubMenu_3 = glutCreateMenu(MySubMenu3);
	glutAddMenuEntry("Pink", 1);
	glutAddMenuEntry("Yellow", 2);
	glutAddMenuEntry("Orange", 3);

	GLint MainMenu_1 = glutCreateMenu(MyMainMenu); //메인메뉴에 서브메뉴 등록
	glutAddSubMenu("Size", SubMenu_1);
	glutAddSubMenu("Speed", SubMenu_2);
	glutAddSubMenu("Color", SubMenu_3);
	glutAttachMenu(GLUT_RIGHT_BUTTON); //오른쪽 마우스 클릭시

	glutDisplayFunc(MyDisplay);
	glutMouseFunc(MyMouseClick);
	glutMotionFunc(MyMouseMove);
	glutKeyboardFunc(MyKeyboard);
	glutSpecialFunc(MyKeyboardMove);
	glutTimerFunc(10, MyTimer, 1);
	glutMainLoop();
	return 0;;
}