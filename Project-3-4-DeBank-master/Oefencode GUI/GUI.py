#importing tkinter library
from tkinter import *

#defining all sub-routines
def get():
    print("Ja toch")

#make the GUI
GetBank=Tk()
GetBank.title("GetBank GUI")
GetBank.geometry("480x320")

#adding a label
Label(GetBank, text="Getbank", font=20  ).grid(row=0, column=0, sticky=W)

#add a button
Button(GetBank, text="Get", width=12, command=get).grid(row=1, column=0, sticky=W)

#everything goes above this, this makes it loop
GetBank.mainloop()