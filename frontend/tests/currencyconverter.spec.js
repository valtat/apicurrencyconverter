import { test, expect } from "@playwright/test";

test("should be able to choose a currency and convert it", async ({ page }) => {
  await page.goto("http://localhost:5173/");
  await page.getByLabel("Amount:").click();
  await page.getByLabel("Amount:").fill("110");
  await page.getByLabel("To:").selectOption("USD");
  await page.getByRole("button", { name: "Convert" }).click();
  await expect(page.locator("#root")).toContainText("EUR is equal to", "USD");
});
