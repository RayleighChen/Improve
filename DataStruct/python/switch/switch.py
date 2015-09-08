# -*- coding: utf-8 -*-

def operation(num1, operater, num2):
    switcher = {
        '+': lambda: num1 + num2,
        '-': lambda: num1 - num2,
        '/': lambda: num1 / num2,
		'*': lambda: num1 * num2, 
    };
    func = switcher.get(operater, "nothing");
    return func();

print operation(9, '/', 3)