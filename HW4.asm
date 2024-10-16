#-------------------------------------------------------
# Name: Giao Quynh Nguyen
# Program name: Homework 4
# Purpose: Given the number of square pizzas sold, 
#   circle pizzas sold, estimate of total pizzas
#   sold in sq feet, calculate total square pizzas 
#   sold in sq feet, total circle pizzas sold in sq 
#   feet, and actual total pizzas sold in sq feet
# Date: 10/31/2023
#------------------------------------------------------
#data segment
    .data
    square_sold: .word 0             # num of square pizzas sold
    circle_sold: .word 0             # num of circle pizzas sold
    estimate_sold: .word 0           # estimate of sold sq ft
    pi: .float 3.14159               # pi for area of circle
    side: .word 14                   # side length = 14 inches
    diameter: .word 9                # 9 inches
    const12_single: .float 144.0     # for conversion from sq inches to sq feet
    prmpt_square: .asciiz "Enter the number of square pizzas sold: "
    prmpt_circle: .asciiz "Enter the number of circle pizzas sold: "
    prmpt_est: .asciiz "Enter the estimate of pizzas sold in square feet: "
    square_res: .asciiz "Total square pizzas sold in sq ft: "
    circ_res: .asciiz "\nTotal circle pizzas sold in sq ft: "
    total_res: .asciiz "\nTotal pizzas sold in sq ft: "

#text segment
    .text
    .globl main
main:
    # get number of square pizzas sold
    li $v0, 4
    la $a0, prmpt_square
    syscall
    li $v0, 5
    syscall
    sw $v0, square_sold
    
    # get number of circle pizzas sold
    li $v0, 4
    la $a0, prmpt_circle
    syscall
    li $v0, 5
    syscall
    sw $v0, circle_sold
    
    # get the estimate of pizzas sold in square feet (Fix: use syscall 5 instead of 6)
    li $v0, 4
    la $a0, prmpt_est
    syscall
    li $v0, 5  # changed from li $v0, 6
    syscall
    sw $v0, estimate_sold
    
    # calculate the total of square pizzas sold in square feet
    lw $t0, side                    # load the side length of the square
    mul $t1, $t0, $t0               # area of a square pizza in square inches
    lw $t2, square_sold             # load the number of square pizzas sold
    mul $t3, $t1, $t2               # calculate total square inches sold
    mtc1 $t3, $f1                   # move the result to a FP register
    cvt.s.w $f1, $f1                # convert int to float
    lwc1 $f3, const12_single         # load the constant for conversion
    div.s $f1, $f1, $f3             # convert into single-precision square feet
    
    # print the result for square pizza
    li $v0, 4
    la $a0, square_res
    syscall
    li $v0, 2                       # print content in $f1
    mov.s $f12, $f1
    syscall
    
    # calculate the total of circle pizzas sold in square feet
    lw $t4, diameter                # load the diameter of the circle (t4)
    li $t6, 2                       # load an immediate to divide by half later (t6)
    
    mtc1 $t4, $f2                   # move the diameter to a FP register (f2)    
    cvt.s.w $f2, $f2                # convert int diameter to single-precision
    
    mtc1 $t6, $f4                   # move the immediate (t6) to a FP register (f4)
    cvt.s.w $f4, $f4                # convert int immediate to single-precision
    
    div.s $f6, $f2, $f4             # divide the diameter (f2) by 2 (f4) = radius (f6)
    
    mul.s $f8, $f6, $f6             # radius squared (f8)
    
    lwc1 $f10, pi                   # load pi value (f10)
    mul.s $f12, $f8, $f10           # area of 1 circle (f12), multiply radius squared (f8) by pi (f10)
    
    lw $t5, circle_sold             # number of circle pizzas sold (t5)
    mtc1 $t5, $f14                  # move t5 into a FP register (f14)
    cvt.s.w $f14, $f14              # convert int circle sold into single-precision
    mul.s $f16, $f12, $f14          # total circle pizzas sold in square inches
    
    lwc1 $f18, const12_single       # load the constant for conversion
    div.s $f16, $f16, $f18          # total circle pizzas sold in square feet
    
    # print the result for circle pizza
    li $v0, 4
    la $a0, circ_res
    syscall
    li $v0, 2                       # print content in $f16
    mov.s $f20, $f16
    syscall
    
    # Add the total pizzas sold in square feet (Sum of square and circle pizzas)
    add.s $f22, $f1, $f16           # Add the square and circle pizza areas
    
    # print the total pizzas sold in square feet
    li $v0, 4
    la $a0, total_res
    syscall
    li $v0, 2                       # print the total in $f22
    mov.s $f12, $f22
    syscall
    
    # halt
    li $v0, 10
    syscall
