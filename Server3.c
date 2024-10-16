#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <time.h> 	//offers access to time-related functions like time()
#include <signal.h> //enables signal handling 

static int timer_expired =  0; //a flag to indicate whether the timer has expired (initially set to 0)
static int alarm_handler(int sig) {timer_expired = 1;} 
/* timer is set to run 1 second then terminate (set the flag to 1, signaling the loop in main to exit)
sig represents a signal number, which will be SIGALRM in this code */

int main(int argc, char *argv[])
{

    int listenfd = 0, connfd = 0; //initialize listening file descriptor and connection file descriptor
    struct sockaddr_in serv_addr; //create server address

    char sendBuff[1025];
    time_t ticks; 
	
	//get port# and time duration from argument
	int port_num = atoi(argv[1]);
	int time_dur = atoi(argv[2]);
	
	//check valid arguments
	if(argc == 3) {
		printf("Arguments provided:\n");
		printf(" Port number: %d\n", atoi(argv[1]));
		printf(" Timeout (seconds): %d\n", atoi(argv[2]));
	} else { 
		fprintf(stderr, "Usage: %s <port_n> <timeout>\n", argv[0]);
		exit(1);
	}

	//create socket
    listenfd = socket(AF_INET, SOCK_STREAM, 0);
    memset(&serv_addr, '0', sizeof(serv_addr));
    memset(sendBuff, '0', sizeof(sendBuff)); 

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    serv_addr.sin_port = htons(port_num); //it was using 34860 (assigned port#) in Server1
	
	//bind socket to address
    bind(listenfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr)); 
	
	//print initial server info & initial system call
	printf("the server uses port# [%d] to listen with [%d] seconds for alarm\n", port_num, time_dur);
	printf("the server will be terminated after [%d] seconds of inactivity\n\n", time_dur);
	system("date; hostname; whoami; ps ; ls -l ");

    listen(listenfd, 10); //listen for connections
	
	//signal handler setup - efficient way
	sigaction(SIGALRM, &(struct sigaction){.sa_handler = alarm_handler}, NULL);
	
	//initial timer setup
	alarm(time_dur); //timer starts counting down as soon as the server starts listening for connections
	
	//accept loop
    while(1)
    {
        connfd = accept(listenfd, (struct sockaddr*)NULL, NULL); //accepting clients
		
		// Check for inactivity and timer expiration (assuming timer_expired and connfd are set)
		if (timer_expired) {
			if (connfd <= 0) {
				// Inactivity termination
				printf("Server timed out due to inactivity.\n");
				exit(1);
			} else {
				// Reset the timer for renewed activity
				timer_expired = 0;
				alarm(time_dur);
				printf("Client activity detected. Resetting timer.\n");
			}
		}
		
		// Craft a specific netstat command (example)
		char netstat_cmd[100];
		snprintf(netstat_cmd, sizeof(netstat_cmd), "netstat -atnp | grep 'ESTABLISHED' | grep %d", port_num);

		// Execute netstat command
		FILE *fp = popen(netstat_cmd, "r");
		if (fp == NULL) {
		  perror("popen");
		  // Handle error (e.g., continue without netstat information)
		  return -1;
		}

		// Print entire output 
		char netstat_output[256];
		while (fgets(netstat_output, sizeof(netstat_output), fp) != NULL) {
		  printf("%s", netstat_output);
		}

		// Close the pipe
		pclose(fp);
		
        ticks = time(NULL); //retrieves current system time since epoch and saves it in variable 'ticks'
        snprintf(sendBuff, sizeof(sendBuff), "%.24s\r\n", ctime(&ticks));
        write(connfd, sendBuff, strlen(sendBuff)); 

        close(connfd);
        sleep(1);
     }
	 //print server termination & final system call
	 printf("the server ends\n");
	 system("date; hostname; whoami; ps ; ls -l ");
	 return 0;
	 
}


