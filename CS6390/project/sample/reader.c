#include <stdio.h> 
#include <fcntl.h> 

/* 
   this program opens a file for reading and 
   keeps reading (and going to sleep) until
   it finds something to read (twice) and then ends 
   
   I suggest you use the same flags I do when opening a file 
    
   save this program into a file reader.c and compile with 
       cc -o reader reader.c

   the instructions to run it are in the other program, writer.c

   */
 
main() 
{ 

  int fd,i, j, oflag;
  char buf[1000];

    oflag = (O_CREAT | O_RDONLY);
    fd = open("tempfile", oflag, 0x1c0);
    if (fd < 0 ) {printf("Error opening\n"); exit(1);}

    j = 0;
    while ( (i = read(fd, buf, 25)) == 0 && j < 10) {
      sleep(5);
      printf("reader: waiting for file contents\n");
      j++;
    };
    printf("reader: # bytes read %d which were: %s\n", i, buf);

    j = 0;
    while ( (i = read(fd, buf, 25)) == 0 && j < 10) {
      sleep(5);
      printf("reader: waiting for file contents\n");
      j++;
    };
    printf("reader: # bytes read %d which were: %s\n", i, buf);
    printf("reader: done\n");
    close(fd);
}






