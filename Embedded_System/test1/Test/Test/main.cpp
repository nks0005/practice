/*
 * Test.cpp
 *
 * Created: 2021-11-04 오후 2:02:48
 * Author : root
 */ 

#define F_CPU 16000000UL
#include <util/delay.h>

#include <avr/io.h>


int main(void)
{
	DDRA = 0xff;
	DDRB = 0x10; 
	
	DDRC = 0xff;
	DDRG = 0xff;
	int value = 0;
	int i = 0;
	while(1){
		i++;
		PORTA = value;
		value++;
		
		PORTC = value;
		PORTG = value;
		
		
		PORTB = 0x10;
		for(int j=0; j<i; j++){
		_delay_ms(1);
		}
		PORTB = 0x00;
		for(int j=0; j<i; j++){
			_delay_ms(1);
		}
		if (i == 1000){
			i=0;}
			if(value == 0xff){
				value = 0;
			}
	}
}

