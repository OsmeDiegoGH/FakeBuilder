package org.fakebuilder.api;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.fakebuilder.entities.Car;
import org.fakebuilder.entities.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import static junit.framework.Assert.assertTrue;
import org.fakebuilder.api.FakeBuilder.ApplyValuesFn;

@RunWith(JUnitParamsRunner.class)
public class FakeBuilderTest {

    private final FakeBuilder builder = new FakeBuilder();

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createNew_ShouldReturnASingleInstance() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //Arrange

        //Act
        Person person = this.builder.createNew(Person.class).build();

        //Assert 
        assertTrue(person != null);
    }

    @Test
    @Parameters({
        "Carlos, Juarez, 25, MALE, true, Ibiza, 2016, false, BLACK",
        "Juan, Carreno, 34, MALE, false, , 0, false, WHITE",
        "Jorge, Fernandez, 29, MALE, true, Jetta, 2015, true, WHITE",
        "Juana, Morales, 22, FEMALE, true, EcoSport, 2017, true, BLUE"
    })
    public void createNew_ShouldReturnASingleInstance_WithCustomValues(
            final String firstName,
            final String lastName,
            final int age,
            final Person.GENDER gender,
            final boolean hasCar,
            final String carName,
            final int carModel,
            final boolean isHybrid,
            final Car.CAR_COLOR carColor
    ) throws Exception {
        //Arrange
        Person person;
        ApplyValuesFn<Person> applyFn = new ApplyValuesFn<Person>() {
            @Override
            public void apply(Person record) {
                record.setFirstName(firstName);
                record.setLastName(lastName);
                record.setAge(age);
                record.setGender(gender);
                record.setHasCar(hasCar);
                record.setCar(new Car(carName, carModel, isHybrid, carColor));
            }
        };

        //Act
        person = this.builder.createNew(Person.class).with(applyFn).build();

        //Assert 
        assertTrue(person.getFirstName().equals(firstName));
        assertTrue(person.getLastName().equals(lastName));
        assertTrue(person.getAge() == age);
        assertTrue(person.getGender().equals(gender));
        assertTrue(person.isHasCar() == hasCar);
        assertTrue(person.getCar().getCarName().equals(carName));
        assertTrue(person.getCar().getModel() == carModel);
        assertTrue(person.getCar().IsHybrid() == isHybrid);
        assertTrue(person.getCar().getColor().equals(carColor));
    }

    @Test
    @Parameters({
        "10",
        "30",
        "60",
        "25"
    })
    public void createNew_ShouldReturnAList_WithCustomSize(int listSize) throws Exception {
        //Arrange
        List<Person> list;

        //Act
        list = this.builder.createList(Person.class).ofSize(listSize).all().build();

        //Assert 
        assertTrue(list.size() == listSize);
    }

    @Test
    @Parameters({
        "10, Carlos, Juarez, 25, MALE, true, Ibiza, 2016, false, BLACK",
        "5, Juan, Carreno, 34, MALE, false, , 0, false, WHITE",
        "15, Jorge, Fernandez, 29, MALE, true, Jetta, 2015, true, WHITE",
        "50, Juana, Morales, 22, FEMALE, true, EcoSport, 2017, true, BLUE"
    })
    public void createNew_ShouldReturnAList_WithCustomSizeAndValues(
            final int listSize,
            final String firstName,
            final String lastName,
            final int age,
            final Person.GENDER gender,
            final boolean hasCar,
            final String carName,
            final int carModel,
            final boolean isHybrid,
            final Car.CAR_COLOR carColor
    ) throws Exception {
        //Arrange
        List<Person> list;
        ApplyValuesFn applyFn = new ApplyValuesFn<Person>() {
            @Override
            public void apply(Person person) {
                person.setFirstName(firstName);
                person.setLastName(lastName);
                person.setAge(age);
                person.setGender(gender);
                person.setHasCar(hasCar);
            }
        };

        //Act
        list = this.builder
                .createList(Person.class)
                .ofSize(listSize)
                .all()
                .with(applyFn)
                .build();

        //Assert 
        assertTrue(list.size() == listSize);
        for (Person person : list) {
            assertTrue(person.getFirstName().equals(firstName));
            assertTrue(person.getLastName().equals(lastName));
            assertTrue(person.getAge() == age);
            assertTrue(person.getGender().equals(gender));
            assertTrue(person.isHasCar() == hasCar);
        }
    }

    @Test
    @Parameters({
        "10," + "2," + "Carlos, Juarez, 25, MALE, true, Ibiza, 2016, false, BLACK",
        "5," + "3," + "Juan, Carreno, 34, MALE, false, , 0, false, WHITE",
        "15," + "5," + "Jorge, Fernandez, 29, MALE, true, Jetta, 2015, true, WHITE",
        "50," + "10," + "Juana, Morales, 22, FEMALE, true, EcoSport, 2017, true, BLUE"
    })
    public void createNew_ShouldReturnAList_WithCustomSizeAndFirstElementsValues(
            final int listSize,
            final int firstElementsToCustomize,
            final String firstName,
            final String lastName,
            final int age,
            final Person.GENDER gender,
            final boolean hasCar,
            final String carName,
            final int carModel,
            final boolean isHybrid,
            final Car.CAR_COLOR carColor
    ) throws Exception {
        //Arrange
        List<Person> list;
        ApplyValuesFn applyFn = new ApplyValuesFn<Person>() {
            @Override
            public void apply(Person person) {
                person.setFirstName(firstName);
                person.setLastName(lastName);
                person.setAge(age);
                person.setGender(gender);
                person.setHasCar(hasCar);
            }
        };

        //Act
        list = this.builder
                .createList(Person.class)
                .ofSize(listSize)
                .theFirst(firstElementsToCustomize)
                .with(applyFn)
                .build();

        //Assert 
        assertTrue(list.size() == listSize);
        for (int i = 0; i < firstElementsToCustomize; i++) {
            Person person = list.get(i);

            assertTrue(person.getFirstName().equals(firstName));
            assertTrue(person.getLastName().equals(lastName));
            assertTrue(person.getAge() == age);
            assertTrue(person.getGender().equals(gender));
            assertTrue(person.isHasCar() == hasCar);
        }
    }

    @Test
    @Parameters({
        "10," + "2," + "Carlos, Juarez, 25, MALE, true, Ibiza, 2016, false, BLACK",
        "5," + "3," + "Juan, Carreno, 34, MALE, false, , 0, false, WHITE",
        "15," + "5," + "Jorge, Fernandez, 29, MALE, true, Jetta, 2015, true, WHITE",
        "50," + "10," + "Juana, Morales, 22, FEMALE, true, EcoSport, 2017, true, BLUE"
    })
    public void createNew_ShouldReturnAList_WithCustomSizeAndLastElementsValues(
            final int listSize,
            final int lastElementsToCustomize,
            final String firstName,
            final String lastName,
            final int age,
            final Person.GENDER gender,
            final boolean hasCar,
            final String carName,
            final int carModel,
            final boolean isHybrid,
            final Car.CAR_COLOR carColor
    ) throws Exception {
        //Arrange
        List<Person> list;
        ApplyValuesFn applyFn = new ApplyValuesFn<Person>() {
            @Override
            public void apply(Person person) {
                person.setFirstName(firstName);
                person.setLastName(lastName);
                person.setAge(age);
                person.setGender(gender);
                person.setHasCar(hasCar);
            }
        };

        //Act
        list = this.builder
                .createList(Person.class)
                .ofSize(listSize)
                .theLast(lastElementsToCustomize)
                .with(applyFn)
                .build();

        //Assert 
        assertTrue(list.size() == listSize);
        for (int i = listSize - lastElementsToCustomize; i < listSize; i++) {
            Person person = list.get(i);

            assertTrue(person.getFirstName().equals(firstName));
            assertTrue(person.getLastName().equals(lastName));
            assertTrue(person.getAge() == age);
            assertTrue(person.getGender().equals(gender));
            assertTrue(person.isHasCar() == hasCar);
        }
    }

    @Test
    @Parameters({
        "20," + "2, Carlos, Juarez, 25, MALE, true, Ibiza, 2016, false, BLACK," + "3,  Juan, Carreno, 34, MALE, false, , 0, false, WHITE",
        "35," + "8, Jorge, Fernandez, 29, MALE, true, Jetta, 2015, true, WHITE," + "4, Juana, Morales, 22, FEMALE, true, EcoSport, 2017, true, BLUE",})
    public void createNew_ShouldReturnAList_WithCustomSizeAndCustomFirstAndNextElementsValues(
            final int listSize,
            //first values
            final int firstElementsToCustomize,
            final String firstName,
            final String lastName,
            final int age,
            final Person.GENDER gender,
            final boolean hasCar,
            final String carName,
            final int carModel,
            final boolean isHybrid,
            final Car.CAR_COLOR carColor,
            //next values
            final int nextElementsToCustomize,
            final String nextFirstName,
            final String nextLastName,
            final int nextAge,
            final Person.GENDER nextGender,
            final boolean nextHasCar,
            final String nextCarName,
            final int nextCarModel,
            final boolean nextIsHybrid,
            final Car.CAR_COLOR nextCarColor
    ) throws Exception {
        //Arrange
        List<Person> list;
        ApplyValuesFn firstApplyFn = new ApplyValuesFn<Person>() {
            @Override
            public void apply(Person person) {
                person.setFirstName(firstName);
                person.setLastName(lastName);
                person.setAge(age);
                person.setGender(gender);
                person.setHasCar(hasCar);
            }
        };
        ApplyValuesFn nextApplyFn = new ApplyValuesFn<Person>() {
            @Override
            public void apply(Person person) {
                person.setFirstName(nextFirstName);
                person.setLastName(nextLastName);
                person.setAge(nextAge);
                person.setGender(nextGender);
                person.setHasCar(nextHasCar);
            }
        };
        int nextTotalIndex = firstElementsToCustomize + nextElementsToCustomize;

        //Act
        list = this.builder
                .createList(Person.class)
                .ofSize(listSize)
                .theFirst(firstElementsToCustomize)
                .with(firstApplyFn)
                .theNext(nextElementsToCustomize)
                .with(nextApplyFn)
                .build();

        //Assert 
        assertTrue(list.size() == listSize);
        for (int i = 0; i < firstElementsToCustomize; i++) {
            Person person = list.get(i);

            assertTrue(person.getFirstName().equals(firstName));
            assertTrue(person.getLastName().equals(lastName));
            assertTrue(person.getAge() == age);
            assertTrue(person.getGender().equals(gender));
            assertTrue(person.isHasCar() == hasCar);
        }

        for (int i = firstElementsToCustomize; i < nextTotalIndex; i++) {
            Person person = list.get(i);

            assertTrue(person.getFirstName().equals(nextFirstName));
            assertTrue(person.getLastName().equals(nextLastName));
            assertTrue(person.getAge() == nextAge);
            assertTrue(person.getGender().equals(nextGender));
            assertTrue(person.isHasCar() == nextHasCar);
        }
    }

    @Test
    @Parameters({
        "10," + "2, Carlos, Juarez, 25, MALE, true, Ibiza, 2016, false, BLACK," + "3, Juan, Carreno, 34, MALE, false, none, 0, false, BLUE, " + "2, Carlos, Juarez, 25, MALE, true, Ibiza, 2016, false, BLACK",
        "35," + "8, Jorge, Fernandez, 29, MALE, true, Jetta, 2015, true, WHITE," + "4, Juana, Morales, 22, FEMALE, true, EcoSport, 2017, true, BLUE," + "8, Jorge, Fernandez, 29, MALE, true, Jetta, 2015, true, WHITE",})
    public void createNew_ShouldReturnAList_WithCustomSizeAndCustomFirstNextAndLastElementsValues(
            final int listSize,
            //first values
            final int firstElementsToCustomize,
            final String firstName,
            final String lastName,
            final int age,
            final Person.GENDER gender,
            final boolean hasCar,
            final String carName,
            final int carModel,
            final boolean isHybrid,
            final Car.CAR_COLOR carColor,
            //next values
            final int nextElementsToCustomize,
            final String nextFirstName,
            final String nextLastName,
            final int nextAge,
            final Person.GENDER nextGender,
            final boolean nextHasCar,
            final String nextCarName,
            final int nextCarModel,
            final boolean nextIsHybrid,
            final Car.CAR_COLOR nextCarColor,
            //last values
            final int lastElementsToCustomize,
            final String lastFirstName,
            final String lastLastName,
            final int lastAge,
            final Person.GENDER lastGender,
            final boolean lastHasCar,
            final String lastCarName,
            final int lastCarModel,
            final boolean lastIsHybrid,
            final Car.CAR_COLOR lastCarColor
    ) throws Exception {
        //Arrange
        List<Person> list;
        ApplyValuesFn firstApplyFn = new ApplyValuesFn<Person>() {
            @Override
            public void apply(Person person) {
                person.setFirstName(firstName);
                person.setLastName(lastName);
                person.setAge(age);
                person.setGender(gender);
                person.setHasCar(hasCar);
            }
        };
        ApplyValuesFn nextApplyFn = new ApplyValuesFn<Person>() {
            @Override
            public void apply(Person person) {
                person.setFirstName(nextFirstName);
                person.setLastName(nextLastName);
                person.setAge(nextAge);
                person.setGender(nextGender);
                person.setHasCar(nextHasCar);
            }
        };
        ApplyValuesFn lastApplyFn = new ApplyValuesFn<Person>() {
            @Override
            public void apply(Person person) {
                person.setFirstName(lastFirstName);
                person.setLastName(lastLastName);
                person.setAge(lastAge);
                person.setGender(lastGender);
                person.setHasCar(lastHasCar);
            }
        };
        int nextTotalIndex = firstElementsToCustomize + nextElementsToCustomize;

        //Act
        list = this.builder
                .createList(Person.class)
                .ofSize(listSize)
                .theFirst(firstElementsToCustomize)
                .with(firstApplyFn)
                .theNext(nextElementsToCustomize)
                .with(nextApplyFn)
                .theLast(lastElementsToCustomize)
                .with(lastApplyFn)
                .build();

        //Assert 
        assertTrue(list.size() == listSize);
        for (int i = 0; i < firstElementsToCustomize; i++) {
            Person person = list.get(i);

            assertTrue(person.getFirstName().equals(firstName));
            assertTrue(person.getLastName().equals(lastName));
            assertTrue(person.getAge() == age);
            assertTrue(person.getGender().equals(gender));
            assertTrue(person.isHasCar() == hasCar);
        }

        for (int i = firstElementsToCustomize; i < nextTotalIndex; i++) {
            Person person = list.get(i);

            assertTrue(person.getFirstName().equals(nextFirstName));
            assertTrue(person.getLastName().equals(nextLastName));
            assertTrue(person.getAge() == nextAge);
            assertTrue(person.getGender().equals(nextGender));
            assertTrue(person.isHasCar() == nextHasCar);
        }

        for (int i = listSize - lastElementsToCustomize; i < listSize; i++) {
            Person person = list.get(i);

            assertTrue(person.getFirstName().equals(lastFirstName));
            assertTrue(person.getLastName().equals(lastLastName));
            assertTrue(person.getAge() == lastAge);
            assertTrue(person.getGender().equals(lastGender));
            assertTrue(person.isHasCar() == lastHasCar);
        }
    }

    @Test
    @Parameters({
        "10," + "2, Carlos, Juarez, 25, MALE, true, Ibiza, 2016, false, BLACK," + "3, Juan, Carreno, 34, MALE, false, none, 0, false, BLUE",
        "35," + "8, Jorge, Fernandez, 29, MALE, true, Jetta, 2015, true, WHITE," + "4, Juana, Morales, 22, FEMALE, true, EcoSport, 2017, true, BLUE",})
    public void createNew_ShouldReturnAList_WithCustomSizeAndCustomFirstAndLastElementsValues(
            final int listSize,
            //first values
            final int firstElementsToCustomize,
            final String firstName,
            final String lastName,
            final int age,
            final Person.GENDER gender,
            final boolean hasCar,
            final String carName,
            final int carModel,
            final boolean isHybrid,
            final Car.CAR_COLOR carColor,
            //last values
            final int lastElementsToCustomize,
            final String lastFirstName,
            final String lastLastName,
            final int lastAge,
            final Person.GENDER lastGender,
            final boolean lastHasCar,
            final String lastCarName,
            final int lastCarModel,
            final boolean lastIsHybrid,
            final Car.CAR_COLOR lastCarColor
    ) throws Exception {
        //Arrange
        List<Person> list;
        ApplyValuesFn firstApplyFn = new ApplyValuesFn<Person>() {
            @Override
            public void apply(Person person) {
                person.setFirstName(firstName);
                person.setLastName(lastName);
                person.setAge(age);
                person.setGender(gender);
                person.setHasCar(hasCar);
            }
        };
        ApplyValuesFn lastApplyFn = new ApplyValuesFn<Person>() {
            @Override
            public void apply(Person person) {
                person.setFirstName(lastFirstName);
                person.setLastName(lastLastName);
                person.setAge(lastAge);
                person.setGender(lastGender);
                person.setHasCar(lastHasCar);
            }
        };

        //Act
        list = this.builder
                .createList(Person.class)
                .ofSize(listSize)
                .theFirst(firstElementsToCustomize)
                .with(firstApplyFn)
                .theLast(lastElementsToCustomize)
                .with(lastApplyFn)
                .build();

        //Assert 
        assertTrue(list.size() == listSize);
        for (int i = 0; i < firstElementsToCustomize; i++) {
            Person person = list.get(i);

            assertTrue(person.getFirstName().equals(firstName));
            assertTrue(person.getLastName().equals(lastName));
            assertTrue(person.getAge() == age);
            assertTrue(person.getGender().equals(gender));
            assertTrue(person.isHasCar() == hasCar);
        }

        for (int i = listSize - lastElementsToCustomize; i < listSize; i++) {
            Person person = list.get(i);

            assertTrue(person.getFirstName().equals(lastFirstName));
            assertTrue(person.getLastName().equals(lastLastName));
            assertTrue(person.getAge() == lastAge);
            assertTrue(person.getGender().equals(lastGender));
            assertTrue(person.isHasCar() == lastHasCar);
        }
    }

}
