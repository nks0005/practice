/*
 * GccApplication1.c
 *
 * Created: 2021-11-04 오후 2:10:14
 * Author : root
 */ 

#define F_CPU 16000000L
#include <avr/io.h>

#include <util/delay.h>


int main(void)
{
	unsigned char value = 128;
	DDRA = 0xff;
	
    /* Replace with your application code */
    while (1) 
    {
		PORTA = value;
		_delay_ms(200);
		
		value >>= 1;
		if(value==0){
			value = 128;
		}
    }
}

