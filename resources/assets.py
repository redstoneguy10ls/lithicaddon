import itertools

from mcresources import ResourceManager, ItemContext, BlockContext, block_states
from mcresources import utils, loot_tables
from mcresources.type_definitions import JsonObject, Json

from consts import *
def generate(rm: ResourceManager):



    for fruit in FRUITS.keys():

        rm.item_model(('not_dried', fruit), 'lithicaddon:item/food/%s' % fruit)
        rm.item_model(('dried', fruit), 'lithicaddon:item/dried/dried_%s' % fruit)
        item_model_property(rm, 'lithicaddon:food/%s' % fruit, [{'predicate': {'firmalife:dry': 1}, 'model': 'lithicaddon:item/dried/%s' % fruit}], {'parent': 'lithicaddon:item/not_dried/%s' % fruit})
        rm.item('food/%s' % fruit).with_lang(lang(fruit))

        for prefix in ('', 'growing_'):
            block = rm.blockstate_multipart('plant/' + fruit + '_' + prefix + 'branch',
                ({'model': 'lithicaddon:block/plant/%s_branch_core' % fruit}),
                ({'down': True}, {'model': 'lithicaddon:block/plant/%s_branch_down' % fruit}),
                ({'up': True}, {'model': 'lithicaddon:block/plant/%s_branch_up' % fruit}),
                ({'north': True}, {'model': 'lithicaddon:block/plant/%s_branch_side' % fruit, 'y': 90}),
                ({'south': True}, {'model': 'lithicaddon:block/plant/%s_branch_side' % fruit, 'y': 270}),
                ({'west': True}, {'model': 'lithicaddon:block/plant/%s_branch_side' % fruit}),
                ({'east': True}, {'model': 'lithicaddon:block/plant/%s_branch_side' % fruit, 'y': 180})
                ).with_tag('tfc:fruit_tree_branch').with_lang(lang('%s Branch', fruit))
            if prefix == '':
                block.with_block_loot({
                    'name': 'lithicaddon:plant/%s_sapling' % fruit,
                    'conditions': loot_tables.all_of(
                        loot_tables.any_of(*[
                            loot_tables.block_state_property('lithicaddon:plant/%s_branch[up=true,%s=true]' % (fruit, direction))
                            for direction in ('west', 'east', 'north', 'south')
                        ]),
                        loot_tables.match_tag('tfc:axes')
                    )
                }, {
                    'name': 'minecraft:stick',
                    'functions': [loot_tables.set_count(1, 4)]
                })
            else:
                block.with_block_loot({'name': 'minecraft:stick', 'functions': [loot_tables.set_count(1, 4)]})
            for part in ('down', 'side', 'up', 'core'):
                rm.block_model('lithicaddon:plant/%s_branch_%s' % (fruit, part), parent='tfc:block/plant/branch_%s' % part, textures={'bark': 'lithicaddon:block/fruit_tree/%s_branch' % fruit})
            rm.blockstate('plant/%s_leaves' % fruit, variants={
                    'lifecycle=flowering': {'model': 'lithicaddon:block/plant/%s_flowering_leaves' % fruit},
                    'lifecycle=fruiting': {'model': 'lithicaddon:block/plant/%s_fruiting_leaves' % fruit},
                    'lifecycle=dormant': {'model': 'lithicaddon:block/plant/%s_dry_leaves' % fruit},
                    'lifecycle=healthy': {'model': 'lithicaddon:block/plant/%s_leaves' % fruit}
                }).with_item_model().with_tag('minecraft:leaves').with_tag('tfc:fruit_tree_leaves').with_lang(lang('%s Leaves', fruit)).with_block_loot({
                    'name': 'lithicaddon:food/%s' % fruit,
                    'conditions': [loot_tables.block_state_property('lithicaddon:plant/%s_leaves[lifecycle=fruiting]' % fruit)]
                }, {
                    'name': 'lithicaddon:plant/%s_leaves' % fruit,
                    'conditions': [loot_tables.any_of(loot_tables.match_tag('forge:shears'), loot_tables.silk_touch())]
                }, {
                    'name': 'minecraft:stick',
                    'conditions': [loot_tables.match_tag('tfc:sharp_tools'), loot_tables.random_chance(0.2)],
                    'functions': [loot_tables.set_count(1, 2)]
                }, {
                    'name': 'minecraft:stick',
                    'conditions': [loot_tables.random_chance(0.05)],
                    'functions': [loot_tables.set_count(1, 2)]
                })
            for life in ('', '_fruiting', '_flowering', '_dry'):
                rm.block_model('lithicaddon:plant/%s%s_leaves' % (fruit, life), parent='block/leaves', textures={'all': 'lithicaddon:block/fruit_tree/%s%s_leaves' % (fruit, life)})

            rm.blockstate(('plant', '%s_sapling' % fruit), variants={'saplings=%d' % i: {'model': 'lithicaddon:block/plant/%s_sapling_%d' % (fruit, i)} for i in range(1, 4 + 1)}).with_lang(lang('%s Sapling', fruit)).with_tag('fruit_tree_sapling')
            rm.block_loot(('plant', '%s_sapling' % fruit), {
                'name': 'lithicaddon:plant/%s_sapling' % fruit,
                'functions': [list({**loot_tables.set_count(i), 'conditions': [loot_tables.block_state_property('lithicaddon:plant/%s_sapling[saplings=%s]' % (fruit, i))]} for i in range(1, 5)), loot_tables.explosion_decay()]
            })

            for stage in range(2, 4 + 1):
                rm.block_model(('plant', '%s_sapling_%d' % (fruit, stage)), parent='tfc:block/plant/cross_%s' % stage, textures={'cross': 'lithicaddon:block/fruit_tree/%s_sapling' % fruit})
            rm.block_model(('plant', '%s_sapling_1' % fruit), {'cross': 'lithicaddon:block/fruit_tree/%s_sapling' % fruit}, 'block/cross')
            rm.item_model(('plant', '%s_sapling' % fruit), 'lithicaddon:block/fruit_tree/%s_sapling' % fruit)
            flower_pot_cross(rm, '%s sapling' % fruit, 'lithicaddon:plant/potted/%s_sapling' % fruit, 'plant/flowerpot/%s_sapling' % fruit, 'lithicaddon:block/fruit_tree/%s_sapling' % fruit, 'lithicaddon:plant/%s_sapling' % fruit)




def item_model_property(rm: ResourceManager, name_parts: utils.ResourceIdentifier, overrides: utils.Json, data: Dict[str, Any]) -> ItemContext:
    res = utils.resource_location(rm.domain, name_parts)
    rm.write((*rm.resource_dir, 'assets', res.domain, 'models', 'item', res.path), {
        **data,
        'overrides': overrides
    })
    return ItemContext(rm, res)

def flower_pot_cross(rm: ResourceManager, simple_name: str, name: str, model: str, texture: str, loot: str, tinted: bool = False):
    rm.blockstate(name, model='lithicaddon:block/%s' % model).with_lang(lang('potted %s', simple_name)).with_tag('minecraft:flower_pots').with_block_loot(loot, 'minecraft:flower_pot')
    rm.block_model(model, parent='minecraft:block/tinted_flower_pot_cross' if tinted else 'minecraft:block/flower_pot_cross', textures={'plant': texture, 'dirt': 'tfc:block/dirt/loam'})
