set terminal svg
set output 'gcdPlot.svg'
set xlabel "nombre de clés"
set ylabel "temps"
set style data linespoints

temps_theorique(x) = (x*x*45/1000000)
# set output "|cat > ./img/gcdSpeedGraph.eps"
plot [0:1000] 'gcdPlot.dat' title 'Temps mesuré',\
       temps_theorique(x) title 'Temps théorique pour une complexité quadratique'
