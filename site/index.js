import { writeOut } from "./util.js";

/* ==========================
 *           Logic
 * ========================== */

function updateDayContentDummy() {
  console.log("This function makes the days changes");
}

/**
 * Advent calendar years.
 */
let years = {
  "2015": {
    1: undefined,
    2: undefined,
    3: undefined,
    4: undefined,
  },
  "2021": {
    1: undefined,
    2: undefined,
  },
  "2023": {},
  "2024": {},
};

/**
 * Import a day's associated module.
 */
async function importDayContentFunction(day, year) {
  try {
    const modulePath = `./years/${year}/${writeOut(day)}.day.js`;
    const module = await import(modulePath);
    console.log(`loading for year${year}/${day}`);
    return module.updateContent;
  } catch (error) {
    console.error(`Failed to load module for ${year}/${day}`);
    console.error(error);
  }
}

/**
 *  Dynamically importing the functions encapsulating each day's logic and storing refs in the year object.
 *  This is done by referencing the years array and then importing the corresponding module.
 *  This function does not search the filesystem as doing so would require a JS runtime.
 */
async function loadFunctions(years) {
  for (const year in years) {
    const yearObj = years[year];
    console.log(yearObj);
    for (const day in yearObj) {
      yearObj[day] = await importDayContentFunction(day, year);
    }
  }
}

/**
 * Populate the year dropdown menu's elements with available years from the 'years' object.
 */
function populateDropdownWithYearOptions(dropdown) {
  for (const year in years) {
    const option = document.createElement("option");
    option.value = year;
    option.textContent = year;
    dropdown.appendChild(option);
  }
}

/* ==============
 *      Setup
 * ============== */

hideDayContent(document);

// NOTE: load the updateContent(document) functions for displaying a day's solution
loadFunctions(years);

/* ============== */

document.addEventListener("DOMContentLoaded", () => {
  const dropdown = document.querySelector(".dropdown select");
  const sidebar = document.querySelector(".sidebar");

  populateDropdownWithYearOptions(dropdown);

  dropdown.addEventListener("change", () => {
    const selectedYear = dropdown.value;
    console.log(`selectedYear: ${selectedYear}`);

    sidebar.querySelectorAll("a").forEach((link) => link.remove());

    if (years[selectedYear]) {
      for (const day in years[selectedYear]) {
        const link = document.createElement("a");
        link.href = "#";
        //   link.href = `#${selectedYear}-${number}`;
        link.textContent = `Day ${day}`;
        link.addEventListener("click", (e) => {
          e.preventDefault();
          const updateDayContent = years[selectedYear][day];
          if (typeof updateDayContent === "function") {
            showDayContent(document);
            updateDayContent(document);
          } else {
            showHomePageContent(document);
            console.error(
              `Could not load updateDayContent function for ${selectedYear}/${day}`,
            );
          }
        });
        sidebar.appendChild(link);
      }
    } else {
      console.log("Clearing day content");
      showHomePageContent(document);
    }
  });
});

/* =============
 *  Appearance
 * ============= */

function showDayContent(document) {
  hideHomeContent(document);
  const dayContent = document.getElementById("day");
  dayContent.style.display = "flex";
}

function hideDayContent(document) {
  const dayContent = document.getElementById("day");
  dayContent.style.display = "none";
}

let savedHomeElement = undefined;

function hideHomeContent(document) {
  const homeElement = document.getElementById("home");
  savedHomeElement = homeElement;
  homeElement.remove();
}

function showHomePageContent(document) {
  hideDayContent(document);
  if (savedHomeElement) {
    const contentWrapper = document.querySelector(".content-wrapper");
    contentWrapper.appendChild(savedHomeElement);
  }
}

/* ============== */
