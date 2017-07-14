#include <stdio.h> 
#include <fcntl.h> 

/* 

   The program waits for some time, then outputs a string to the
   tempfile, then waits some more, then outputs another string to 
   the tempfile. 
   
   I suggest you use the same flags I do when opening a file 
    
   save this program into a file writer.c and compile with 
       cc -o writer writer.c

   then run in by first typing the reader (the other sample program) 
       reader &

   which is placed in the background, then start the writer 
   (this program ) by typing
       writer &

   after running the program remove the temporary file tempfile
       rm tempfile

   before logging out make sure you do not leave any processes running
   in the background. The unix commands on the web page tell you how
   */
 
main() 
{ 

  int fd,i, j, oflag;
  char buf[1000];

    oflag = (O_TRUNC | O_CREAT | O_WRONLY);
    fd = open("tempfile", oflag, 0x1c0);
    if (fd < 0 ) {printf("Error opening\n"); exit(1);}
    sleep(4);                       
    printf("writer: writting to file\n");
    write(fd, "This is a test", 15); 
    sleep(4);       
    printf("writer: writting to file\n");
    write(fd, "Second test", 12); 
    close(fd);
    printf("writer: done\n");
}






