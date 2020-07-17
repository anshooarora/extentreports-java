<div class='json-tree' id='code-block-json-${index}'></div>
<script>
function jsonTreeCreate${index}() { 
  document.getElementById('code-block-json-${index}').innerHTML = JSONTree.create(${code}); 
}
jsonTreeCreate${index}();
</script>
