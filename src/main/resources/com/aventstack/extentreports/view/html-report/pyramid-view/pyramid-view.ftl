<div id='pyramid-view' class='view hide'>
	<div class='card-panel transparent np-v'>
		<h5>Pyramid</h5>
		<div class='row'>
		<#if mainTestGroups?? && categoryContext?? && categoryContext?size != 0>
			<div class='col s4'>
				<div class="card-panel">
					<span class='right label cyan white-text'>Main Test Groups</span><p>&nbsp;</p>
					
					<table>
						<tr>
							<th>Name</th>
							<th>Passed</th>
							<th>Failed</th>
							<th>Others</th>
						</tr>
						<#list categoryContext as category>
						<#if mainTestGroups?contains(category.name?lower_case)>
						<tr>
							<td>${ category.name }</td>
							<td>${ category.passed }</td>
							<td>${ category.failed }</td>
							<td>${ category.others }</td>
						</tr>
						</#if>
						</#list>
					</table>
				</div>
				</div>
			<div class='col s12'>
			<div style="width:450px;height:450px;" id="pyramid"></div>
			</div>
			</#if>
	
		</div>
	</div>
</div>
<script>

function TestGroup(name, anzahl) {
    this.name = name;
    this.anzahl = anzahl;
};
            var width = 450,
                height = 450,
                radius = Math.min(width, height) / 2;
            
            var color = d3.scale.ordinal()
                .range(["#3366cc", "#dc3912", "#ff9900","rgb(101,154,302)","rgb(122,175,323)", "rgb(144,197,345)", "rgb(165,218,366)"]);
            
            var svg = d3.select("#pyramid").append("svg")
                .attr("width", width)
                .attr("height", height)
                .append("g")
               
               
      
         function drawn(data){
                	  var pyramid = d3.pyramid()
                      .size([width,height])
                      .value(function(d) { return d.anzahl; });
                	  var line = d3.svg.line()
                      .interpolate('linear-closed')
                      .x(function(d,i) { return d.x; })
                      .y(function(d,i) { return d.y; });
                  
                  var g = svg.selectAll(".pyramid-group")
                      .data(pyramid(data))
                      .enter().append("g")
                      .attr("class", "pyramid-group");
                  
                  g.append("path")
                      .attr("d", function (d){ return line(d.coordinates); })
                      .style("fill", function(d) { return color(d.name); });
                  
                  g.append("text")
                      .attr({
                        	"y": function (d,i) {
                              if(d.coordinates.length === 4) {
                                  return (((d.coordinates[0].y-d.coordinates[1].y)/2)+d.coordinates[1].y) + 5;
                              } else {
                                  return (d.coordinates[0].y + d.coordinates[1].y)/2 + 10;
                              }
                          },
                          "x": function (d,i) { return width/2;}
                      })
                      .style("text-anchor", "middle")
                      .text(function(d) { return d.name; });
                  };
                </script>
 		