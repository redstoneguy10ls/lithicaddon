from typing import Dict, List, NamedTuple, Sequence, Optional, Literal, Tuple, Any, Set

class Rock(NamedTuple):
    category: str
    sand: str

Fruit = NamedTuple('Fruit', min_temp=float, max_temp=float, min_rain=float, max_rain=float)

FRUITS: Dict[str, Fruit] = {
    'mulberry': Fruit(20, 30, 300, 500)
}

SIMPLE_ITEMS = ('aluminum_lid',)

ROCKS: Dict[str, Rock] = {
    'granite': Rock('igneous_intrusive', 'white'),
    'diorite': Rock('igneous_intrusive', 'white'),
    'gabbro': Rock('igneous_intrusive', 'black'),
    'shale': Rock('sedimentary', 'black'),
    'claystone': Rock('sedimentary', 'brown'),
    'limestone': Rock('sedimentary', 'white'),
    'conglomerate': Rock('sedimentary', 'green'),
    'dolomite': Rock('sedimentary', 'black'),
    'chert': Rock('sedimentary', 'yellow'),
    'chalk': Rock('sedimentary', 'white'),
    'rhyolite': Rock('igneous_extrusive', 'red'),
    'basalt': Rock('igneous_extrusive', 'red'),
    'andesite': Rock('igneous_extrusive', 'red'),
    'dacite': Rock('igneous_extrusive', 'yellow'),
    'quartzite': Rock('metamorphic', 'white'),
    'slate': Rock('metamorphic', 'yellow'),
    'phyllite': Rock('metamorphic', 'brown'),
    'schist': Rock('metamorphic', 'green'),
    'gneiss': Rock('metamorphic', 'green'),
    'marble': Rock('metamorphic', 'yellow')
}


def lang(key: str, *args) -> str:
    return ((key % args) if len(args) > 0 else key).replace('_', ' ').replace('/', ' ').title()