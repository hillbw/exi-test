### A fragmented self-tutorial on BSON to learn the encoding process

01.  \x2f  = document length (byte 1)  |
02.  \x01  = document length (byte 2)  | 303
03.  \x00  = document length (byte 3)  |
04.  \x00  = document length (byte 4)  |
05.  \x03  = begin element: embedded document
  06.  \x67  = 'g'
  07.  \x70  = 'p'
  08.  \x78  = 'x'
  09.  \x00  = terminate e_name  
  10.  \x25  = embedded document length (byte 1)  | 
  11.  \x01  = embedded document length (byte 2)  |  293
  12.  \x00  = embedded document length (byte 3)  |
  13.  \x00  = embedded document length (byte 4)  |
  14.  \x01  = begin element: floating point double
  15.  \x76  = 'v'
  16.  \x65  = 'e'
  17.  \x72  = 'r'
  18.  \x73  = 's'
  19.  \x69  = 'i'
  20.  \x6f  = 'o'
  21.  \x6e  = 'n'
  22.  \x00  = terminate e_name
  23.  \x9a  = floating point double (byte 1)
  24.  \x99  = floating point double (byte 2)
  25.  \x99  = floating point double (byte 3)
  26.  \x99  = floating point double (byte 4)
  27.  \x99  = floating point double (byte 5)
  28.  \x99  = floating point double (byte 6)
  29.  \xf1  = floating point double (byte 7)
  30.  \x3f  = floating point double (byte 8)
  31.  \x03  = begin element: embedded document
    32.  \x74  = 't'
    33.  \x72  = 'r'
    34.  \x6b  = 'k'
    35.  \x00  = terminate e_name
    36.  \x7b  = embedded document length (byte 1)  |  
    37.  \x00  = embedded document length (byte 2)  |  123
    38.  \x00  = embedded document length (byte 3)  |  
    39.  \x00  = embedded document length (byte 4)  |  
    40.  \x02  = begin element: string
    41.  \x6e  = 'n'
    42.  \x61  = 'a'
    43.  \x6d  = 'm'
    44.  \x65  = 'e'
    45.  \x00  = terminate e_name
    46.  \x09  = string length (byte 1) |
    47.  \x00  = string length (byte 2) | 9
    48.  \x00  = string length (byte 3) |
    49.  \x00  = string length (byte 4) |
    50.  \x55  = 'U'
    51.  \x6e  = 'n'
    52.  \x74  = 't'
    53.  \x69  = 'i'
    54.  \x74  = 't' 
    55.  \x6c  = 'l'
    56.  \x65  = 'e' 
    57.  \x64  = 'd'
    58.  \x00  = terminate string
    59.  \x03  = begin element: embedded document
      60.  \x74  = 't'
      61.  \x72  = 'r'
      62.  \x6b  = 'k'
      63.  \x73  = 's'
      64.  \x65  = 'e'
      65.  \x67  = 'g'
      66.  \x00  = terminate e_name
      67.  \x5b  = embedded document length (byte 1)  |
      68.  \x00  = embedded document length (byte 2)  | 91
      69.  \x00  = embedded document length (byte 3)  |
      70.  \x00  = embedded document length (byte 4)  |
71.  \x03  = begin element: embedded document
72.  \x74  = 't'
73.  \x72  = 'r'
74.  \x6b  = 'k'
75.  \x70  = 'p'
76.  \x74  = 't'
77.  \x00  = terminate e_name
78.  \x4f  = embedded document length (byte 1)  |  
79.  \x00  = embedded document length (byte 2)  | 79
80.  \x00  = embedded document length (byte 3)  |
81.  \x00  = embedded document length (byte 4)  |
82.  \x01  = begin element: double
83.  \x6c  = 'l'
84.  \x61  = 'a' 
85.  \x74  = 't'
86.  \x00  = terminate e_name 
87.  \x00  = floating point double (byte 1) |
88.  \x00  = floating point double (byte 2) | 36.62711977958679
89.  \x00  = floating point double (byte 3) |
90.  \x76  = floating point double (byte 4) |
91.  \x45  = floating point double (byte 5) |
92.  \x50  = floating point double (byte 6) |
93.  \x42  = floating point double (byte 7) |
94.  \x40  = floating point double (byte 8) |
.  \x01  = begin element: double
.  \x65  = 'e'
.  \x6c  = 'l'
.  \x65  = 'e'
.  \x00  = terminate e_name
.  \x00  = floating point double (byte 1) |
.  \x00  = floating point double (byte 1) |
.  \x00  = floating point double (byte 1) | 
.  \xc0  = floating point double (byte 1) |
.  \xcc  = floating point double (byte 1) | 
.  \xcc  = floating point double (byte 1) |
.  \x3d  = floating point double (byte 1) | 
.  \x40  = floating point double (byte 1) |
.  \x02  = begin element: string
.  \x74  = 't'
.  \x69  = 'i'
.  \x6d  = 'm'
.  \x65  = 'e'
.  \x00  = terminate e_name
.  \x19  = string length (byte 4) |
.  \x00  = string length (byte 4) | 25
.  \x00  = string length (byte 4) | 
.  \x00  = string length (byte 4) |
.  \x32  = '2'
.  \x30  = '0'
.  \x31  = '1' 
.  \x34  = '4'
.  \x2d  = '-' 
.  \x30  = '0'
.  \x39  = '9' 
.  \x2d  = '-'
.  \x30  = '0' 
.  \x35  = '5'
.  \x54  = 'T'
.  \x31  = '1'
.  \x38  = '8'
.  \x3a  = ':'
.  \x30  = '0'
.  \x37  = '7'
.  \x3a  = ':'
.  \x30  = '0'
.  \x33  = '3'
.  \x2e  = '.'
.  \x30  = '0'
.  \x30  = '0'
.  \x30  = '0'
.  \x5a  = 'Z'
.  \x00  = terminate string
.  \x01
.  \x6c
.  \x6f
.  \x6e 
.  \x00
.  \x00 
.  \x00
.  \xb6 
.  \xaf
.  \x3c 
.  \x6c
.  \x5e 
.  \xc0
.  \x00 
.  \x00
.  \x00 
.  \x02
.  \x63
.  \x72
.  \x65 
.  \x61
.  \x74 
6f
72 
00
0f 
00
00 
00
47 
61
72 
6d
69
6e
20 
43
6f 
6e
6e 
65
63 
74
00 
03
6d 
65
74 
61
64
61
74 
61
00 
69
00 
0000 036c 696e 6b00 3b00
0000 0274 6578 7400 0f00 0000 4761 726d
696e 2043 6f6e 6e65 6374 0002 6872 6566
0013 0000 0063 6f6e 6e65 6374 2e67 6172
6d69 6e2e 636f 6d00 0002 7469 6d65 0019
0000 0032 3031 342d 3039 2d30 3554 3138
3a30 373a 3033 2e30 3030 5a00 0000 00
