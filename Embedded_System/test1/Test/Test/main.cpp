/*
 * 12161609_안형빈
 * 10주차 과제
 */ 

#define F_CPU 16000000UL
#include <util/delay.h>

#include <avr/io.h>


void speak(char input){
	PORTA = (1 << input);
	_delay_ms(500);
	PORTA = 0;
	_delay_ms(500);
}

int main(void)
{
		// 도 : 0
		// 레 : 1
		// 미 : 2
		// 파 : 3
		// 솔 : 4
		// 라 : 5
		// 시 : 6
		
	// 비행기 악보
	// 라 솔 파 솔 라 라 라 
	// 솔 솔 솔 라 라 라 
	// 라 솔 파 솔 라 라 라 
	// 솔 솔 라 솔 파

	char music[] = {5,4,3,4,5,5,5,4,4,4,5,5,5,5,4,3,4,5,5,5,4,4,5,4,3};
	int music_length = sizeof(music);
	
	int i = 0;
	
	DDRA = 0xff;
	
	while(1){
		speak(music[i]);
		i++;

		if (i == music_length){
			break;
		}		
	}
}

