#ifndef COMPUTE_ACTIONS_H
#define COMPUTE_ACTIONS_H

#define ACTION_OPCODE_NOP       0
#define ACTION_OPCODE_SEND_ON   1
#define ACTION_OPCODE_SEND_OFF  2
#define ACTION_OPCODE_DELAY     3

extern void initActions(void);

#endif