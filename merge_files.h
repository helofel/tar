#ifndef MERGE_FILES_H
#define MERGE_FILES_H

void print_file_data(struct stat *);
struct stat * file_header(char *);
int merg_files(int, char **);

#endif
