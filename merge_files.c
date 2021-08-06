#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <time.h>
#include <stdio.h>
#include "merge_files.h"


void print_file_data(struct stat * sb){
    printf("\n\n\n\n");
    printf("File type:                ");

    switch (sb -> st_mode & S_IFMT) {
        case S_IFBLK:  printf("block device\n");            break;
        case S_IFCHR:  printf("character device\n");        break;
        case S_IFDIR:  printf("directory\n");               break;
        case S_IFIFO:  printf("FIFO/pipe\n");               break;
        case S_IFLNK:  printf("symlink\n");                 break;
        case S_IFREG:  printf("regular file\n");            break;
        case S_IFSOCK: printf("socket\n");                  break;
        default:       printf("unknown?\n");                break;
    }

    printf("I-node number:            %ld\n", (long) sb -> st_ino);
    printf("Mode:                     %lo (octal)\n", (unsigned long) sb -> st_mode);
    printf("Link count:               %ld\n", (long) sb -> st_nlink);
    printf("Ownership:                UID=%ld   GID=%ld\n", (long) sb -> st_uid, (long) sb -> st_gid);
    printf("Preferred I/O block size: %ld bytes\n", (long) sb -> st_blksize);
    printf("File size:                %lld bytes\n", (long long) sb -> st_size);
    printf("Blocks allocated:         %lld\n", (long long) sb -> st_blocks);
    printf("Last status change:       %s", ctime(&sb -> st_ctime));
    printf("Last file access:         %s", ctime(&sb -> st_atime));
    printf("Last file modification:   %s", ctime(&sb -> st_mtime));

}

struct stat * file_header(char* file){
    struct stat * sb = malloc(sizeof(struct stat));

    if(stat(file, sb) == -1){
        perror("stat");
        exit(EXIT_FAILURE);
    }

    print_file_data(sb);

    return sb;
}

int merge_files(int argc, char** argv){
    int write_fd;
    int read_from_fd;
    char ch;
    char new_line_buf = '\n';

    write_fd = open(argv[1], O_CREAT | O_RDWR | O_TRUNC | O_APPEND);
    
    for(int index = 2; index < argc; ++index){
        read_from_fd = open(argv[index], O_RDONLY);
        if( write_fd < 0 ){
            perror( argv[index] );
            exit(EXIT_FAILURE); 
        }
        
        write(write_fd, file_header(argv[index]), sizeof(struct stat));
        write(write_fd, &new_line_buf, sizeof(char));

        while(read(read_from_fd, &ch, 1) == 1){
            write(write_fd, &ch, 1);
        }

        close(read_from_fd);
    }
    close(write_fd);

    return write_fd;
}

int main(int argc, char** argv){
    merge_files(argc, argv); //tar file

    //file_header(argv[1]);    //tar file header data
    exit(EXIT_SUCCESS);
}
