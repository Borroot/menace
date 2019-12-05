#!/usr/bin/env python3.7

import matplotlib.pyplot as plt
import numpy as np

import sys

np.set_printoptions(threshold=np.inf)

# Read the array with the results of player 1.
lines = sys.stdin.readlines()
runs  = [] # num of games * num of runs

for line in lines:
    runs.append(eval(line))  # cast string to array

# Make the figure.
fig, ax = plt.subplots(figsize=(10,8))
counts  = [] # (num of games + 1) * num of runs

# Create all the plots.
for results in runs:
    # Create the y values for the plot.
    count = 0
    ys    = [count]

    for i in results:
        count += i
        ys.append(count)

    counts.append(ys)

    # Create the x values for the plot.
    xs = range(len(results) + 1)

    # Create the actual plot.
    ax.plot(xs, ys, color='grey')

# Create the average line.
xs = range(len(counts[0]))
av_ys  = [0]
min_ys = [0]
max_ys = [0]

counts = np.array(counts)

for i in range(counts[0].size - 1):
    lst = counts[:,i]
    y   = sum(lst) / len(lst)

    av_ys.append(y)
    min_ys.append(min(lst))
    max_ys.append(max(lst))

ax.fill_between(xs, min_ys, max_ys, color='lightgrey')
ax.plot(xs, av_ys, color='black', linewidth=3)

# Set the labels for the plot and save it as an image.
ax.set(xlabel='round (n)', ylabel='performance (loss=-1, tie=0, win=+1)')
fig.savefig('fig.png')

plt.show()
