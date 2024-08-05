{
 "cells": [
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {},
   "outputs": [
    {
     "ename": "",
     "evalue": "",
     "output_type": "error",
     "traceback": [
      "\u001b[1;31mRunning cells with 'Python 3.12.4' requires the ipykernel package.\n",
      "\u001b[1;31mRun the following command to install 'ipykernel' into the Python environment. \n",
      "\u001b[1;31mCommand: 'c:/Users/staj_eren.yavuz/AppData/Local/Programs/Python/Python312/python.exe -m pip install ipykernel -U --user --force-reinstall'"
     ]
    }
   ],
   "source": [
    "# Create a list of ten 10s\n",
    "elements = [10] * 10\n",
    "\n",
    "# Perform the operation in a for loop\n",
    "for i in range(10):\n",
    "    # Decrease the value of every element by 0.1 of its value\n",
    "    elements = [x - 0.1 * x for x in elements]\n",
    "    \n",
    "    # Decrease the elements between the index of i and 0 once more\n",
    "    for j in range(i + 1):\n",
    "        elements[j] -= 0.1 * elements[j]\n",
    "    \n",
    "    # Calculate the total of the elements and print out\n",
    "    total = sum(elements)\n",
    "    print(f\"Total after iteration {i + 1}: {total}\")\n",
    "\n",
    "# Final total after all iterations\n",
    "final_total = sum(elements)\n",
    "print(f\"Final total: {final_total}\")\n"
   ]
  }
 ],
 "metadata": {
  "kernelspec": {
   "display_name": "Python 3",
   "language": "python",
   "name": "python3"
  },
  "language_info": {
   "name": "python",
   "version": "3.12.4"
  }
 },
 "nbformat": 4,
 "nbformat_minor": 2
}
