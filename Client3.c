#include <netdb.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <arpa/inet.h> 

int main(int argc, char *argv[])
{
	char ip[INET_ADDRSTRLEN];  //store server IP
    int port;                 //storing port# from input
	
	//do the following system calls in the beginning of the program run
	system(" date; hostname; whoami ; who | grep `whoami` ");
	system(" netstat -aont | grep \" `hostname -i`:3486[0-9] \" ");
	
	FILE *fp;
	char *hostname;
	
	fp = popen("hostname -i", "r"); //open a pipe for reading "hostname -i" output
	if(fp == NULL) {
		perror("popen");
		exit(1);
	}
	fclose(fp); //close the pipe
	
	//read the output from the pipe
	fgets(hostname, INET_ADDRSTRLEN, fp);
	
	//remove trailing newline character
	hostname[strcspn(hostname, "\n")] = 0;
	
	/*use the retrieved hostname for inet_pton, 1st argument
	if the input is a direct IP address, it will bypass popen call */
	if(inet_pton(AF_INET, argv[1], &ip) <=0) 
	{
		fprintf(stderr, "Invalid server IP address\n");
		exit(1);
	}
	
	port = atoi(argv[2]); //get port#, 2nd argument
	
	/* check valid arguments & update variables
	Similar to Server2, we have another input for server IP (IPv4), which is why
	it's 3 in the if statement. */
	if(argc == 3) {//1st change
		printf("Arguments provided:\n");
		printf(" Server IP: %s\n", argv[1]);
		printf(" Port number: %d\n", atoi(argv[2]));
	} else { 
		fprintf(stderr, "Usage: %s <server_ip> <port_n>\n", argv[0]);
		exit(1);
	}
	
	//print initial client info & initial system call
	printf("the client is to connect to the server of IP# %s and Port# %d\n\n", argv[1], argv[2]);
	printf("the client will be terminated.\n"); //Note: prompt on eLearning isn't complete
	system("date; hostname; whoami; ps ; ls -l ");
	
	int sockfd = 0, n = 0;
    char recvBuff[1024];
    struct sockaddr_in serv_addr; 

	//socket creation
    memset(recvBuff, '0',sizeof(recvBuff));
    if((sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        printf("\n Error : Could not create socket \n");
        return 1;
    } 
	memset(&serv_addr, '0', sizeof(serv_addr)); 

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(port); 
    
	
    system(" date; hostname; whoami "); //from Server1
    system(" netstat -aont | grep \" `hostname -i`:34860 \" "); //from Server1
	
	printf("\n timeClient3: connecting to %s Port#=%d \n", ip, port);
	
	//convert IP address string to binary network address
    if(inet_pton(AF_INET, argv[1], &serv_addr.sin_addr)<=0)
    {
        printf("\n inet_pton error occured\n");
        return 1;
    } 
	 
	//connect to server
    if( connect(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0)
    {
       printf("\n Error : Connect Failed \n");
       return 1;
    } 

    printf("\n timeClient3: connected to timeServer. \n");
	system("ps");
    system(" netstat -aont | grep \":34860 \" "); //from Server1
    printf("\n\n");

    while ( (n = read(sockfd, recvBuff, sizeof(recvBuff)-1)) > 0)
    {
        recvBuff[n] = 0;
        if(fputs(recvBuff, stdout) == EOF)
        {
            printf("\n Error : Fputs error\n");
        }
    } 

    if(n < 0)
    {
        printf("\n Read error \n");
	   exit(0);
    } 

    printf("\n timeClient3: now terminated. \n");
	
	//do the following system calls at the end of the program run
	system(" date; hostname; whoami ; who | grep `whoami` ");
	system(" netstat -aont | grep \" `hostname -i`:3486[0-9] \" ");
	
	//print client termination & final system call
	printf("\n the client ends \n");
    system("date; hostname; whoami ; ps; ls -l ");
	
    return 0;
}