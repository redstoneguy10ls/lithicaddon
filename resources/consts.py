from typing import Dict, List, NamedTuple, Sequence, Optional, Literal, Tuple, Any, Set


Fruit = NamedTuple('Fruit', min_temp=float, max_temp=float, min_rain=float, max_rain=float)

FRUITS: Dict[str, Fruit] = {
    'mulberry': Fruit(20, 30, 300, 500)
}

SIMPLE_ITEMS = ('aluminum_lid',)


def lang(key: str, *args) -> str:
    return ((key % args) if len(args) > 0 else key).replace('_', ' ').replace('/', ' ').title()