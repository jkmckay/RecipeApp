# Readme

## Key architectural decisions
1. Package structure is based off of a modularised, package-by-feature approach. While the app isn't modularised, I do find this structure quite clear and easy to navigate so I've emulated it where it makes sense.
2. DI - I've used various frameworks and patterns but I'm a fan of Hilt due to its ubiquity, how powerful it can be and at this point familiarity .
3. I originally started with my usual MVVM + UDF but thought I'd give MVI a shot given their similarity and how it'd been a while since I'd done it .
4. "Clean Architecture" - I'm using a heavily cut down version that primarily takes advantage of the concept of Use Cases, which are represented by the fun interfaces in the domain layer.  Given the sample, it's very minor, but the idea is that the "use cases" encapsulate business logic within them and keep them strictly compartmentalised. 
5. Mappers - I've approached  transforming the DTOs to domain models in a very strict manner. From the json, nullability isn't explicit and as such I've treated every DTO property as nullable. For the domain representation however, determining what properties are "optional" is very much a business question. If there's no prep or cook time, would the recipe be considered malformed? What if there is a single null value ingredient at the end of the list? As such, if there's a Recipe that's missing properties where I cannot determine its validity, I treat it as malformed. I've also catered for empty lists, lists of nulls, etc.
## Trade-offs
* With the time constraints, which I already exceeded - UI was heavily de-prioritised in favour of architecture/project structure/state management/lifecycle management.
* Unhappy paths/error scenario only accessible through code.

## What I would improve with more time
* String resources
* More robust url handling (re; Coil)
* Compose Previews
* Improve Composables overall, probably break them down a bit smaller with the idea to make them re-usable plus a bit more polish
* Theming
* Logging
* Accessibility labels - might consider adding prefixes for things like images, certain titles, etc. Additionally, Talkback is reading prep/cook times units wrong. Need to ensure 'm' and 'h' are read as minutes, hours, etc.
* Unit testing could go a little further in terms of handling malformed data
* Certain architectural concerns would be better served if abstracted out so they could be re-used. E.g. the MVI intents, ViewModel states.
* UI test or some form of automation test
* Add a Recipe Detail Screen and give Jetpack Navigation 3 a shot.
